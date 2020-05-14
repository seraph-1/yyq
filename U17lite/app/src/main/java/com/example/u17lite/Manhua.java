package com.example.u17lite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Manhua {
    @Id
    private String name;
    private String cover;
    private String tag;
    private String detial;
    private String comicid;
    private String chapterid;
    private boolean collect;
    private boolean history;

    @Generated(hash = 2002604834)
    public Manhua() {
    }

    @Generated(hash = 2118836841)
    public Manhua(String name, String cover, String tag, String detial, String comicid, String chapterid, boolean collect, boolean history) {
        this.name = name;
        this.cover = cover;
        this.tag = tag;
        this.detial = detial;
        this.comicid = comicid;
        this.chapterid = chapterid;
        this.collect = collect;
        this.history = history;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    public void setComicid(String comicid) {
        this.comicid = comicid;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChapterid() {
        return chapterid;
    }

    public boolean isHistory() {
        return history;
    }

    public boolean isCollect() {
        return collect;
    }

    public String getComicid() {
        return comicid;
    }

    public String getDetial() {
        return detial;
    }

    public String getTag() {
        return tag;
    }

    public String getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public boolean getCollect() {
        return this.collect;
    }

    public boolean getHistory() {
        return this.history;
    }
}
