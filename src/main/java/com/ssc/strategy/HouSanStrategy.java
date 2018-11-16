package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.prize.HousanGenPrize;
import com.ssc.service.LotteryStrategyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class HouSanStrategy extends BaseStrategy{


    @Resource
    protected HousanGenPrize housanGenPrize;
    @Resource
    protected LotteryStrategyService lotteryStrategyService;
    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        boolean result = false;

        /*String base = "000 001 002 003 004 005 006 007 008 009 010 011 012 013 014 015 016 017 018 019 020 021 022 023 024 025 026 027 028 029 030 031 032 033 034 035 036 037 038 039 040 041 042 043 044 045 046 047 048 049 050 051 052 053 054 055 056 057 058 059 060 061 062 063 064 065 066 067 068 069 070 071 072 073 074 075 076 077 078 079 090 091 092 093 094 095 096 097 098 099 200 204 206 208 209 210 212 213 215 216 218 219 220 221 222 223 224 225 226 227 228 229 230 231 232 233 234 235 236 237 238 239 240 246 248 249 250 251 252 253 254 255 256 257 258 259 260 264 266 268 269 270 272 273 275 276 278 279 290 294 296 298 299 300 304 306 308 309 310 311 312 313 314 315 316 317 318 319 320 321 322 323 324 325 326 327 328 329 330 331 332 333 334 335 336 337 338 339 340 344 346 348 349 350 351 352 353 354 355 356 357 358 359 360 364 366 368 369 370 371 372 373 374 375 376 377 378 379 390 394 396 398 399 400 401 402 403 404 405 406 407 408 409 410 411 412 413 414 415 416 417 418 419 420 421 422 423 424 425 426 427 428 429 430 431 432 433 434 435 436 437 438 439 440 441 442 443 444 445 446 447 448 449 450 451 452 453 454 455 456 457 458 459 460 461 462 463 464 465 466 467 468 469 470 471 472 473 474 475 476 477 478 479 490 491 492 493 494 495 496 497 498 499 500 504 510 512 513 520 521 522 523 524 530 531 532 533 534 540 550 551 552 553 554 555 556 557 558 559 560 564 566 568 569 570 572 573 575 576 578 579 590 594 596 598 599 600 601 602 603 604 610 611 612 613 614 620 621 622 623 624 630 631 632 633 634 640 641 642 643 644 650 651 652 653 654 655 656 657 658 659 660 661 662 663 664 665 666 667 668 669 670 671 672 673 674 675 676 677 678 679 690 691 692 693 694 695 696 697 698 699 700 704 710 711 712 713 714 720 721 722 723 724 730 731 732 733 734 740 744 750 751 752 753 754 755 756 757 758 759 760 764 766 768 769 770 771 772 773 774 775 776 777 778 779 790 794 796 798 799 800 801 802 803 804 810 812 813 820 821 822 823 824 830 831 832 833 834 840 842 843 850 851 852 853 854 855 856 857 858 859 860 861 862 863 864 865 866 867 868 869 870 872 873 875 876 878 879 890 891 892 893 894 895 896 897 898 899 900 901 902 903 904 910 912 913 914 920 921 922 923 924 930 931 932 933 934 940 941 942 943 944 950 951 952 953 954 955 956 957 958 959 960 961 962 963 964 965 966 967 968 969 970 971 972 973 974 975 976 977 978 979 990 992 993 994 995 996 997 998";
        String hou3 = "" + realNo.getBai() + realNo.getShi() + realNo.getGe();
        if (base.indexOf(hou3) >= 0) {
            return true;
        } else {
            return false;
        }*/
      /* List<TCFFCPRIZE> genPrizeList = (List<TCFFCPRIZE>) calNo;
        String hou3Prize = realNo.getPrize().substring(2, 5);
        if (null != genPrizeList && genPrizeList.size() > 0) {
            for (TCFFCPRIZE tcffcprize : genPrizeList) {
                String prize = tcffcprize.getPrize();
                if (StringUtils.isNotEmpty(prize) && prize.equals(hou3Prize)) {
                    result = true;
                    break;
                }
            }
        }*/

        List<String> genPrizeList = (List<String>) calNo;
        String hou3Prize = realNo.getPrize().substring(2, 5);
        if (null != genPrizeList && genPrizeList.size() > 0) {
            for (String prize : genPrizeList) {
                if (StringUtils.isNotEmpty(prize) && prize.equals(hou3Prize)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
       //取最近1680期的最热788注
    }

    @Override
    public Object calBetNum(Date time) {
//        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
//        tcffcprize.setTime(time);
//        housanGenPrize.getGenPrizeNumsStr(tcffcprize, tcffcprize);
//        List<TCFFCPRIZE> result = housanGenPrize.genPrizeList;
//        return result;
        return lotteryStrategyService.queryLatestHotNums(time, 5000, 800);
    }
}
