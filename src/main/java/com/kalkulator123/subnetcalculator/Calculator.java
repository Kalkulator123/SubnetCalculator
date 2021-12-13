package com.kalkulator123.subnetcalculator;

import javafx.scene.Scene;
import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum CalculatorValues {
    NetworkClass,
    IPAddress,
    SubnetMask,
    SubnetBits,
    MaximumSubnets,
    FirstOctetRange,
    HexIPAddress,
    WildCardMask,
    MaskBits,
    HostsPerSubnet,
    HostAddressRange,
    SubnetID,
    BroadcastAddress,
    SubnetBitmap
}

public class Calculator {
    private Scene scene;
    private final EnumMap<CalculatorValues, String> valueMap =
            new EnumMap<>(CalculatorValues.class);

    public boolean ended = false;
    public int id;
    private String[] ip;
    public Calculator(Scene scene, String networkClass, String ipAdress, int subnetByIndex) {
        this.scene = scene;
        if (!isValidIPAddress(ipAdress)) {
            ipAdress = "192.168.1.1";
        }
        startBlock(ipAdress, networkClass, subnetByIndex);


    }
    public void startBlock(String ipAdress, String networkClass, int subnetByIndex){
        ip = ipAdress.split("\\.");
        setNetworkClass(networkClass);
        setIPAddress();
        setSubnetByIndex(subnetByIndex);
    }
    public static boolean isValidIPAddress(String ip)
    {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;
        Pattern p = Pattern.compile(regex);
        if (ip == "") {
            return false;

        }
        Matcher m = p.matcher(ip);
        return m.matches();
    }
    public void setNetworkClass(String className) {
        switch (className) {
            case "B" -> {
                setValue(CalculatorValues.NetworkClass, className);
                setValue(CalculatorValues.FirstOctetRange, "128-191");
            }
            case "C" -> {
                setValue(CalculatorValues.NetworkClass, className);
                setValue(CalculatorValues.FirstOctetRange, "192-223");
            }
            case "A" -> {
                setValue(CalculatorValues.NetworkClass, className);
                setValue(CalculatorValues.FirstOctetRange, "1-126");
            }
        }
    }

    public void setIPAddress() {
        String[] octet = getValue(CalculatorValues.FirstOctetRange).split("-");
        Pair<Integer, Integer> range = new Pair<>(Integer.parseInt(octet[0]), Integer.parseInt(octet[1]));
        if(!(Integer.parseInt(ip[0]) >= range.getKey() && Integer.parseInt(ip[0]) <= range.getValue())) {
            ip[0]=range.getKey().toString();
        }

        String ipAddress = String.join(".", ip);
        setValue(CalculatorValues.IPAddress, ipAddress);
        setHexIPAddress();
    }

    private void setHexIPAddress() {
        StringBuilder hexIP = new StringBuilder();

        for (String value : ip) {
            String hexValue = Integer.toHexString(Integer.parseInt(value));
            hexValue = (hexValue.length() == 1) ? "0" + hexValue : hexValue;
            hexIP.append(hexValue).append(".");
        }
        hexIP.deleteCharAt(hexIP.lastIndexOf("."));

        setValue(CalculatorValues.HexIPAddress, hexIP.toString().toUpperCase());
    }

    public void setSubnetByIndex(int index) {
        id = index;
        setValue(CalculatorValues.SubnetMask, getSubnetMaskList().get(index));
        setValue(CalculatorValues.SubnetBits, getSubnetBitsList().get(index));
        setValue(CalculatorValues.MaskBits, getMaskBitsList().get(index));
        setValue(CalculatorValues.MaximumSubnets, getMaximumSubnetsList().get(index));
        setValue(CalculatorValues.HostsPerSubnet, getHostsPerSubnetList().get(index));
        setSubnetBitmap();
        setSubnetID();
        setBroadcastAddress();
        setHostAddressRange();
        setWildcardMask(getValue(CalculatorValues.SubnetMask));
    }

    private void setSubnetID() {
        String[] ipB = getValue(CalculatorValues.IPAddress).split("\\.");
        for(int i = 0; i < ipB.length; i++) {
            ipB[i] = Integer.toBinaryString(Integer.parseInt(ipB[i]));
        }

        String[] maskB = getValue(CalculatorValues.SubnetMask).split("\\.");
        for(int i = 0; i < maskB.length; i++) {
            maskB[i] = Integer.toBinaryString(Integer.parseInt(maskB[i]));
        }

        String[] broadcastAddress = new String[4];
        for(int i = 0; i < broadcastAddress.length; i++) {
            BigInteger b1 = new BigInteger(ipB[i], 2);
            BigInteger b2 = new BigInteger(maskB[i], 2);
            broadcastAddress[i] = String.valueOf(Integer.parseInt(String.valueOf(b1.and(b2))));
        }

        setValue(CalculatorValues.SubnetID, String.join(".", broadcastAddress));
    }

    public List<String> getSubnetMaskList() {
        List<String> subnetMasksList = new ArrayList<>();
        boolean[][] bitArray = new boolean[4][8];
        int closeOn = switch (getValue(CalculatorValues.NetworkClass)) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> 0;
        };

        for(int i = 0; i < bitArray.length; i++) {
            for(int j = 0; j < 8; j++) {
                bitArray[i][j] = i < closeOn;
            }
        }

        for (int i = closeOn; i < bitArray.length; i++) {
            for(int j = 0; j < bitArray[i].length; j++) {
                subnetMasksList.add(toSubnetMask(bitArray));
                if(i == bitArray.length - 1 && ((j == bitArray[i].length - 2 || j == bitArray[i].length - 1))) {
                    break;
                }
                bitArray[i][j] = true;
            }
        }

        return subnetMasksList;
    }

    private void setWildcardMask(String subnetMask) {
        try {
            String[] subnetMaskArray = subnetMask.split("\\.");
            StringBuilder wildcardMask = new StringBuilder();
            wildcardMask.append("0.");

            for (int i = 1; i < subnetMaskArray.length; i++) {
                wildcardMask.append(255 - Integer.parseInt(subnetMaskArray[i])).append(".");
            }
            wildcardMask.deleteCharAt(wildcardMask.length() - 1);

            setValue(CalculatorValues.WildCardMask, wildcardMask.toString());
        }
        catch (Exception e) {
            setValue(CalculatorValues.WildCardMask, "NULL");
        }
    }

    public int subnetClass(){
        int value=0;
        switch (getValue(CalculatorValues.NetworkClass)) {
            case "A" -> value = 8;
            case "B" -> value = 16;
            case "C" -> value = 24;
        }
        return value;
    }
    public List<String> getSubnetBitsList() {
        List<String> subnetBitsList = new ArrayList<>();
        int subnetMaskLength = 8*4;
        int subnetBits = 0;
        for (int i = subnetClass() - 1; i < subnetMaskLength - 2; i++) {
            subnetBitsList.add(String.valueOf(subnetBits++));
        }

        return subnetBitsList;
    }
    public List<String> getMaskBitsList() {
        List<String> subnetBitsList = new ArrayList<>();

        int subnetMaskLength = 8*4;
        int subnetBits = subnetClass();

        for (int i = subnetBits; i <= subnetMaskLength - 2; i++) {
            subnetBitsList.add(String.valueOf(subnetBits++));
        }

        return subnetBitsList;
    }

    public List<String> getMaximumSubnetsList() {
        List<String> subnetBitsList = getSubnetBitsList();
        List<String> maximumSubnetsList = new ArrayList<>();
        for (String s : subnetBitsList) {
            StringBuilder sb = new StringBuilder();
            sb.append(Math.pow(2, Integer.parseInt(s)))
                    .deleteCharAt(sb.length() - 1)
                    .deleteCharAt(sb.length() - 1);
            maximumSubnetsList.add(sb.toString());
        }
        return maximumSubnetsList;
    }
    public List<String> getHostsPerSubnetList() {
        List<String> subnetBitsList = getMaskBitsList();
        List<String> hostsPerSubnetList = new ArrayList<>();
        for (String s : subnetBitsList) {
            StringBuilder sb = new StringBuilder();
            sb.append(Math.pow(2, (32 - Integer.parseInt(s))) - 2)
                    .deleteCharAt(sb.length() - 1)
                    .deleteCharAt(sb.length() - 1);
            hostsPerSubnetList.add(sb.toString());
        }
        return hostsPerSubnetList;
    }

    private void setSubnetBitmap() {
        String[] subnetBitmap = new String[4];
        int subnetBits = Integer.parseInt(getValue(CalculatorValues.SubnetBits));
        int maskBits = Integer.parseInt(getValue(CalculatorValues.MaskBits)) - subnetBits;

        switch (getValue(CalculatorValues.NetworkClass)) {
            case "A" -> {
                subnetBitmap[0] = "0";
                maskBits -= 1;
            }
            case "B" -> {
                subnetBitmap[0] = "10";
                maskBits -= 2;
            }
            case "C" -> {
                subnetBitmap[0] = "110";
                maskBits -= 3;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(subnetBitmap[i] == null) {
                subnetBitmap[i] = "";
            }
            for (int j = subnetBitmap[i].length(); j < 8; j++) {
                if(maskBits-- > 0) {
                    subnetBitmap[i] += "n";
                    continue;
                }
                if(subnetBits-- > 0) {
                    subnetBitmap[i] += "s";
                    continue;
                }
                subnetBitmap[i] += "h";
            }
        }
        setValue(CalculatorValues.SubnetBitmap, String.join(".", subnetBitmap));
    }

    private void setBroadcastAddress() {
        String[] ipB = getValue(CalculatorValues.SubnetID).split("\\.");
        for(int i = 0; i < ipB.length; i++) {
            ipB[i] = Integer.toBinaryString(Integer.parseInt(ipB[i]));
        }

        String[] maskB = getValue(CalculatorValues.SubnetMask).split("\\.");
        for(int i = 0; i < maskB.length; i++) {
            maskB[i] = Integer.toBinaryString(Integer.parseInt(maskB[i]));
        }

        String[] broadcastAddress = new String[4];
        for(int i = 0; i < broadcastAddress.length; i++) {
            BigInteger b1 = new BigInteger(ipB[i], 2);
            maskB[i] = maskB[i].replace('0', '2')
                    .replace('1', '0').replace('2', '1');
            BigInteger b2 = new BigInteger(maskB[i], 2);
            broadcastAddress[i] = String.valueOf(Integer.parseInt(String.valueOf(b1.or(b2))));
        }

        setValue(CalculatorValues.BroadcastAddress, String.join(".", broadcastAddress));
    }

    private void setHostAddressRange() {
        String[] sID = getValue(CalculatorValues.SubnetID).split("\\.");
        String[] bAdd = getValue(CalculatorValues.BroadcastAddress).split("\\.");
        sID[sID.length - 1] = String.valueOf(Integer.parseInt(sID[sID.length - 1]) + 1);
        bAdd[bAdd.length - 1] = String.valueOf(Integer.parseInt(bAdd[bAdd.length - 1]) - 1);

        String hostAddressRange = String.join(".", sID) + "-" + String.join(".", bAdd);
        setValue(CalculatorValues.HostAddressRange, hostAddressRange);
    }

    private String toSubnetMask(boolean[][] bitArray) {
        StringBuilder subnetMask = new StringBuilder();
        for (boolean[] bits : bitArray) {
            int n = 0;
            for (boolean b : bits) {
                n = (n << 1) + (b ? 1 : 0);
            }
            subnetMask.append(n).append(".");
        }
        subnetMask.deleteCharAt(subnetMask.lastIndexOf("."));

        return subnetMask.toString();
    }

    private void setValue(CalculatorValues calculatorValues, String value) {
        valueMap.put(calculatorValues, value);
    }

    public String getValue(CalculatorValues calculatorValues) {
        if(valueMap.get(calculatorValues) == null) {
            setSubnetByIndex(0);
            if(valueMap.get(calculatorValues) == null) {
                return "";
            }
        }
        return valueMap.get(calculatorValues);
    }
}
