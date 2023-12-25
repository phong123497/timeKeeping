//================================================================================
// ID: SRC009
// ProjectName: TIMEKEEPING
// SystemName: 勤怠管理システム
// ProgramName:QRチェックイン画面
// ClassName:Src009Service
//
// <<Modification History>>
// Version  | Date       | Updated By           | Content
// ---------+------------+----------------------+---------------------------------
// 01.00.00 | 2023/11/16 | Rikkei               | 新規作成
//================================================================================
package com.timekeeping.management.service.impl;

import com.timekeeping.common.entity.*;
import com.timekeeping.common.exception.CommonException;
import com.timekeeping.common.repository.*;
import com.timekeeping.management.dto.scr009.Scr009DtoInterface;
import com.timekeeping.management.dto.scr009.Src009ListDto;
import com.timekeeping.management.form.Src009Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class Src009Service {


    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkdayDetailRepository workdayDetailRepository;

    @Autowired
    private MstWorkTimeRepository mstWorkTimeRepository;

    @Autowired
    private WorkDayRepository workDayRepository;

    @Transactional
    public Src009ListDto scanQR(Src009Form form)  throws  Exception {
        Date date = new Date();
        Timestamp scanTime = new Timestamp(date.getTime());
        CommonException commonException = new CommonException();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String scanTimeFormat = timeFormat.format(date);

        // if qrcode not exits
        Integer qrcodeCount = qrCodeRepository.checkQrcodeNotExitByQrCode(form.getQrcode());
        //if qrcode exits but deleteflg =1
        Integer qrcodeCountDelFlg = qrCodeRepository.checkQrcodeNotExitByQrCodeAndDeleteFlg(form.getQrcode(), 1);

        if (qrcodeCount == 0 || qrcodeCountDelFlg > 0) {
            commonException.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        QrCodeEntity qrcode = qrCodeRepository.getQrCodeEntityByQrCodeAndDeletedFg(form.getQrcode(), 0);
        UserEntity userEntity = userRepository.getUserEntityByEmployeeId(qrcode.getEmployeeId());
        WorkPlaceEntity workPlace = workPlaceRepository.getWorkPlaceEntityByWorkPlaceId(userEntity.getWorkPlaceId());
        MstWorktimeEntity mstWorktime = mstWorkTimeRepository.getMstWorktimeEntityByWorktimeId(workPlace.getWorktimeId());
        /// get mstTime  and covert to string
        String getStart = timeFormat.format(mstWorktime.getStartTime());
        String getRestEndTime = timeFormat.format(mstWorktime.getRestEndTime());
        String getEndTime = timeFormat.format(mstWorktime.getEndTime());

        Integer workDayId = createWorkDayId(workPlace.getWorkPlaceId());
        WorkdayDetailEntity workdayDetail = workdayDetailRepository.getWorkdayDetailEntityByWorkdayIdAndEmployeeId(workDayId, qrcode.getEmployeeId());

        // check null or  not null of workdayDetail
        if (workdayDetail == null) {
            WorkdayDetailEntity workdayDetailEntity = new WorkdayDetailEntity();
            workdayDetailEntity.setWorkdayId(workDayId);
            workdayDetailEntity.setEmployeeId(qrcode.getEmployeeId());
            workdayDetailEntity.setCreatedId("1");
            workdayDetailEntity.setCreatedDt(scanTime);
            workdayDetailEntity.setUpdatedDt(scanTime);
            ///save workdayDetail with workDayId and getEmployeeId
            workdayDetailRepository.save(workdayDetailEntity);
            // After saved workdayDetailEntity to repo will get the workdayDetailId of WorkdayDetailEntity
            WorkdayDetailEntity savedWorkDetail = workdayDetailRepository.getWorkdayDetailEntityByWorkdayIdAndEmployeeId(workDayId, qrcode.getEmployeeId());
            /// workdetailId of workRecode table not exits
            WorkRecordEntity createWorkRecord = new WorkRecordEntity();
            //createWorkRecord
            createWorkRecord.setWorkdayDetailId(savedWorkDetail.getWorkdayDetailId());
            createWorkRecord.setWorkPlaceId(workPlace.getWorkPlaceId());
            createWorkRecord.setCreatedId("0");
            createWorkRecord.setCreatedDt(scanTime);
            createWorkRecord.setUpdatedDt(scanTime);

            if (scanTimeFormat.compareTo(getStart) <=0 || (scanTimeFormat.compareTo(getStart) >0 && scanTimeFormat.compareTo(getRestEndTime) < 0)){
                createWorkRecord.setWorkStartTimeMorning(scanTime);
            } else if (scanTimeFormat.compareTo(getRestEndTime) <=0 || (scanTimeFormat.compareTo(getRestEndTime) > 0 && scanTimeFormat.compareTo(getEndTime)<0)) {
                createWorkRecord.setWorkStartTimeAfternoon(scanTime);
            }
            try {
                workRecordRepository.save(createWorkRecord);
            } catch (Exception e) {
                commonException.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            WorkdayDetailEntity existWorkDetail = workdayDetailRepository.getWorkdayDetailEntityByWorkdayIdAndEmployeeId(workDayId, qrcode.getEmployeeId());
            existWorkDetail.setUpdatedDt(scanTime);
            workdayDetailRepository.save(existWorkDetail);
            WorkRecordEntity workRecordUpdate = workRecordRepository.getWorkRecordEntityByAndWorkdayDetailId(existWorkDetail.getWorkdayDetailId());
            workRecordUpdate.setUpdatedDt(scanTime);
            /// hiện tại đang xét các trường hợp cho
            if (scanTimeFormat.compareTo(getStart) <=0){
                // setWorkStartTimeMorning not update get old value
            } else if (scanTimeFormat.compareTo(getStart) > 0 && scanTimeFormat.compareTo(getRestEndTime) <0) {
                workRecordUpdate.setWorkEndTimeMorning(scanTime);
            } else if (scanTimeFormat.compareTo(getRestEndTime) <0 ) {
                // setWorkStartTimeAfternoon not update get old value
            } else if ((scanTimeFormat.compareTo(getRestEndTime) >0 && scanTimeFormat.compareTo(getEndTime) <0) || scanTimeFormat.compareTo(getEndTime) >=0) {
                workRecordUpdate.setWorkEndTimeAfternoon(scanTime);
            }
            workRecordRepository.save(workRecordUpdate);
        }
        ///  get 5 record last of workDetailTable
        List<Scr009DtoInterface> list5Record = workdayDetailRepository.getRecordInWorkDetail(workDayId, workPlace.getWorkPlaceId());
        Src009ListDto resEntity = new Src009ListDto();
        resEntity.setEmployeeScanList(list5Record);
        return resEntity;
    }
    /**
     * create workDayID
     *
     * @param workPlaceId
     */
    public Integer createWorkDayId(Integer workPlaceId) {
        Integer workDayId = null;
        Date date = new Date();
        Timestamp scanTime = new Timestamp(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        WorkdayEntity workdayEntity = workDayRepository.getWorkdayEntityByWorkdayCus(format.format(date));
        if (workdayEntity == null) {
            WorkdayEntity createWorkDay = new WorkdayEntity();
            createWorkDay.setCreatedId("0");
            createWorkDay.setCreatedDt(scanTime);
            createWorkDay.setWorkPlaceId(workPlaceId);
            createWorkDay.setOpeningTime(scanTime);
            createWorkDay.setCreatedDt(scanTime);
            createWorkDay.setWorkday(scanTime);

            workDayRepository.save(createWorkDay);
            WorkdayEntity getWorkDay = workDayRepository.getWorkdayEntityByWorkdayCus(format.format(date));
            workDayId = getWorkDay.getWorkdayId();
        } else {
            WorkdayEntity getWorkDay = workDayRepository.getWorkdayEntityByWorkdayCus(format.format(date));
            workDayId = getWorkDay.getWorkdayId();
        }
        return workDayId;
    }

    /**
     * create qrcode function
     * @param employeeId
     * @param deletedFg
     * @return qrCodeEntity
     */
    public QrCodeEntity createQrcode(String employeeId, Integer deletedFg) {
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        boolean checkExist = qrCodeRepository.existsQrCodeEntityByEmployeeIdAndDeletedFg(employeeId, deletedFg);
        QrCodeEntity qrCodeEntity = new QrCodeEntity();
        UserEntity userEntity = userRepository.getUserEntityByEmployeeId(employeeId);
        String createQrCode = generateNewQRCode();
        if (checkExist) {
            qrCodeEntity.setQrCode(createQrCode);
            qrCodeEntity.setDeletedFg(0);
            qrCodeEntity.setCreatedDt(time);
            qrCodeEntity.setEmployeeId(employeeId);
            qrCodeRepository.save(qrCodeEntity);
            userRepository.save(userEntity);
        } else {
            qrCodeRepository.save(qrCodeEntity);
        }
        return qrCodeEntity;
    }
    /**
     * create qrcode
     *
     * @return newQrcode
     */
    public String generateNewQRCode() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        StringBuilder newQrcode = new StringBuilder();
        // create qrcode has length <10
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(charSet.length());
            newQrcode.append(charSet.charAt(index));
        }
        return newQrcode.toString();
    }
}
