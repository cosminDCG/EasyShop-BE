package EasyShop.dto;

import java.util.Date;

public class BanDTO {

    private int id;
    private int bannedBy;
    private int bannedUser;
    private int banDays;
    private Date endDate;
    private String reason;

    public BanDTO(int id, int bannedBy, int bannedUser,int banDays, Date endDate, String reason) {
        this.id = id;
        this.bannedBy = bannedBy;
        this.bannedUser = bannedUser;
        this.banDays = banDays;
        this.endDate = endDate;
        this.reason = reason;
    }

    public BanDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(int bannedBy) {
        this.bannedBy = bannedBy;
    }

    public int getBannedUser() {
        return bannedUser;
    }

    public void setBannedUser(int bannedUser) {
        this.bannedUser = bannedUser;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getBanDays() {
        return banDays;
    }

    public void setBanDays(int banDays) {
        this.banDays = banDays;
    }
}
