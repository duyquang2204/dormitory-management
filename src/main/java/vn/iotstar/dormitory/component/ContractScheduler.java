package vn.iotstar.dormitory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.iotstar.dormitory.entity.HopDong;
import vn.iotstar.dormitory.repository.HopDongRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class ContractScheduler {

    @Autowired
    private HopDongRepository hopDongRepository;

    // Run every day at 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateContractStatus() {
        System.out.println("Running Scheduled Task: Check Contract Expiration...");
        LocalDate today = LocalDate.now();
        List<HopDong> contracts = hopDongRepository.findAll();
        for (HopDong hd : contracts) {
            if ("Còn hạn".equals(hd.getTrangThai()) && hd.getNgayKetThuc() != null) {
                if (hd.getNgayKetThuc().isBefore(today)) {
                    hd.setTrangThai("Đã hết hạn");
                    hopDongRepository.save(hd);
                    System.out.println("Auto updated contract " + hd.getMaHopDong() + " to Đã hết hạn.");
                }
            }
        }
    }
}
