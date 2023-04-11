package models;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String customerld;

    public User(String name, String customerld) {
        this.name = name;
        this.customerld = customerld;
    }

    public String getName() {
        return name;
    }


    public String getCustomerld() {
        return customerld;
    }

    // kiểm tra CCCD đúng không
    public boolean isCustomerldExisted(String cccd) {
        if (cccd.length() == 12) {
            try {
                long socccd = Long.parseLong(cccd);
                return kiemTraSoCCCD(socccd);
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

    //kiểm tra cccd có phải là số hay không
    private boolean kiemTraSoCCCD(long socccd) {
        if (socccd <= 0 ) {
            return false;
        }
        else {
            int count = 0;
            long socccdchinh = socccd;
            while (socccd > 0) {
                count++;
                socccd /= 10;
            }
            if (count != 10 && count != 11) {
                return false;
            }
            else {
                return kiemTraMaTinh(socccdchinh);
            }
        }
    }

    // kiểm tra mã tỉnh
    private boolean kiemTraMaTinh(long socccd) {
        int maTinh;
        maTinh = (int) (socccd / 1000000000);
        switch (maTinh) {
            case 1: case 2: case 4: case 6: case 8: case 10: case 11: case 12: case 14: case 15: case 17: case 19: case 20:
            case 22: case 24: case 25: case 26: case 27: case 30: case 31: case 33: case 34: case 35: case 36: case 37:
            case 38: case 40: case 42: case 44: case 45: case 46: case 48: case 49: case 51: case 52: case 54: case 56:
            case 58: case 60: case 62: case 64: case 66: case 67: case 68: case 70: case 72: case 74: case 75: case 77:
            case 79: case 80: case 82: case 83: case 84: case 86: case 87: case 89: case 91: case 92: case 93: case 94:
            case 95: case 96:
                return true;
            default : return false;
        }
    }
}
