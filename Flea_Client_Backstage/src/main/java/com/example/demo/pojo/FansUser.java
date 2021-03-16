package com.example.demo.pojo;

/**
 * @author 周杨清
 * @date 2021/3/13 1:56
 */
public class FansUser {
    private ESUser ESuser;
    private Boolean fansJudge;//判断该用户，是否关注

    public ESUser getESuser() {
        return ESuser;
    }

    public void setESuser(ESUser ESuser) {
        this.ESuser = ESuser;
    }

    public Boolean getFansJudge() {
        return fansJudge;
    }

    public void setFansJudge(Boolean fansJudge) {
        this.fansJudge = fansJudge;
    }
}
