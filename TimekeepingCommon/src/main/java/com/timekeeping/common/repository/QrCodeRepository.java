package com.timekeeping.common.repository;

import com.timekeeping.common.dto.QrcodeDto;
import com.timekeeping.common.entity.QrCodeEntity;
import com.timekeeping.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Integer> {
    QrCodeEntity  getQrCodeEntityByQrCodeAndDeletedFg (String qrCode, Integer deleteFlg);

    boolean existsQrCodeEntityByEmployeeIdAndDeletedFg( String qrCode, Integer deleteFlg);


    QrCodeEntity getQrCodeEntityByEmployeeIdAndDeletedFg(String employeesId, Integer delFlg );
    boolean existsQrCodeEntityByQrCode (String qrcode);


    /**
     *
     * @param qrcode
     * @return count
     */
    @Query(value = "select count(*) from QrCodeEntity as q where  q.qrCode= :qrcode")
    Integer checkQrcodeNotExitByQrCode(@Param("qrcode") String qrcode);

    /**
     *
     * @param qrcode
     * @param deleteFlg
     * @return count
     */
    @Query(value = "select count(*) from QrCodeEntity as q where  q.qrCode= :qrcode  and q.deletedFg= :deleteFlg")
    Integer checkQrcodeNotExitByQrCodeAndDeleteFlg(@Param("qrcode") String qrcode, @Param("deleteFlg")Integer deleteFlg);


}
