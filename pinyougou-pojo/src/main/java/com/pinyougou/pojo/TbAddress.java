package com.pinyougou.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_address")
public class TbAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String provinceId;

    private String cityId;

    private String townId;

    private String mobile;

    private String address;

    private String contact;

    private String isDefault;

    private String notes;

    private Date createDate;

    private String alias;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbAddress tbAddress = (TbAddress) o;

        if (id != null ? !id.equals(tbAddress.id) : tbAddress.id != null) return false;
        if (userId != null ? !userId.equals(tbAddress.userId) : tbAddress.userId != null) return false;
        if (provinceId != null ? !provinceId.equals(tbAddress.provinceId) : tbAddress.provinceId != null) return false;
        if (cityId != null ? !cityId.equals(tbAddress.cityId) : tbAddress.cityId != null) return false;
        if (townId != null ? !townId.equals(tbAddress.townId) : tbAddress.townId != null) return false;
        if (mobile != null ? !mobile.equals(tbAddress.mobile) : tbAddress.mobile != null) return false;
        if (address != null ? !address.equals(tbAddress.address) : tbAddress.address != null) return false;
        if (contact != null ? !contact.equals(tbAddress.contact) : tbAddress.contact != null) return false;
        if (isDefault != null ? !isDefault.equals(tbAddress.isDefault) : tbAddress.isDefault != null) return false;
        if (notes != null ? !notes.equals(tbAddress.notes) : tbAddress.notes != null) return false;
        if (createDate != null ? !createDate.equals(tbAddress.createDate) : tbAddress.createDate != null) return false;
        return alias != null ? alias.equals(tbAddress.alias) : tbAddress.alias == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (provinceId != null ? provinceId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (townId != null ? townId.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }
}