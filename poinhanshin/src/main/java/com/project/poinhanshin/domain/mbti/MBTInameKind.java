package com.project.poinhanshin.domain.mbti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MBTInameKind {
    private String kindCd; // 품번
    private String animalname; // 동물품종 이름
    private String MBTIcategory; // MBTI 유형

    public MBTInameKind(String kindCd, String animalname, String MBTIcategory) {
        this.kindCd = kindCd;
        this.animalname = animalname;
        this.MBTIcategory = MBTIcategory;
    }
}
