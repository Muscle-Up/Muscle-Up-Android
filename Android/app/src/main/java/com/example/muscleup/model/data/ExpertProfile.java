package com.example.muscleup.model.data;

public class ExpertProfile {

    private String name;
    private String introduction;
    private int age;
    private String type;
    private String certificateName;
    private String acquisitionDate;
    private byte[] userImage;
    private byte[] certificateImage;

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public byte[] getCertificateImage() {
        return certificateImage;
    }
}
