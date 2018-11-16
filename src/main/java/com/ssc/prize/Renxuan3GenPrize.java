package com.ssc.prize;

import com.ssc.model.TCFFCPRIZE;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class Renxuan3GenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("renxuan3File.txt");
        allFile = new File("renxuan3AllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize, TCFFCPRIZE curPrize) {
        String result = "";
        Map<String, Integer> keyCntMap = new HashMap<>();

        String wan = String.valueOf(curPrize.getWan());
        String qian = String.valueOf(curPrize.getQian());
        String bai = String.valueOf(curPrize.getBai());
        String shi = String.valueOf(curPrize.getShi());
        String ge = String.valueOf(curPrize.getGe());
        this.countMapKey(keyCntMap, wan);
        this.countMapKey(keyCntMap, qian);
        this.countMapKey(keyCntMap, bai);
        this.countMapKey(keyCntMap, shi);
        this.countMapKey(keyCntMap, ge);

        int oneRoadCnt = (keyCntMap.get("1")==null ?0 : keyCntMap.get("1"))
                            + (keyCntMap.get("4")==null ?0 : keyCntMap.get("4"))
                                + (keyCntMap.get("7")==null ?0 : keyCntMap.get("7"));

        int twoRoadCnt = (keyCntMap.get("2")==null ?0 : keyCntMap.get("2"))
                + (keyCntMap.get("5")==null ?0 : keyCntMap.get("5"))
                + (keyCntMap.get("8")==null ?0 : keyCntMap.get("8"));

        if(oneRoadCnt > twoRoadCnt) {
            result = "002 005 008 032 035 038 062 065 068 092 095 098 302 305 308 332 335 338 362 365 368 392 395 398 602 605 608 632 635 638 662 665 668 692 695 698 902 905 908 932 935 938 962 965 968 992 995 998 020 023 026 029 050 053 056 059 080 083 086 089 320 323 326 329 350 353 356 359 380 383 386 389 620 623 626 629 650 653 656 659 680 683 686 689 920 923 926 929 950 953 956 959 980 983 986 989 200 203 206 209 230 233 236 239 260 263 266 269 290 293 296 299 500 503 506 509 530 533 536 539 560 563 566 569 590 593 596 599 800 803 806 809 830 833 836 839 860 863 866 869 890 893 896 899";
        } else {
            result = "013 014 016 017 019 031 034 036 037 039 041 043 046 047 049 061 063 064 067 069 071 073 074 076 079 091 093 094 096 097 103 104 106 107 109 130 134 136 137 139 140 143 146 147 149 160 163 164 167 169 170 173 174 176 179 190 193 194 196 197 301 304 306 307 309 310 314 316 317 319 340 341 346 347 349 360 361 364 367 369 370 371 374 376 379 390 391 394 396 397 401 403 406 407 409 410 413 416 417 419 430 431 436 437 439 460 461 463 467 469 470 471 473 476 479 490 491 493 496 497 601 603 604 607 609 610 613 614 617 619 630 631 634 637 639 640 641 643 647 649 670 671 673 674 679 690 691 693 694 697 701 703 704 706 709 710 713 714 716 719 730 731 734 736 739 740 741 743 746 749 760 761 763 764 769 790 791 793 794 796 901 903 904 906 907 910 913 914 916 917 930 931 934 936 937 940 941 943 946 947 960 961 963 964 967 970 971 973 974 976";
        }
        return result;

    }

    private void countMapKey(Map<String, Integer> map, String key){
        if(map.get(key) == null) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key)+1);
        }
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        if (null != genPrize) {
            return true;
        }
        return false;
    }
}
