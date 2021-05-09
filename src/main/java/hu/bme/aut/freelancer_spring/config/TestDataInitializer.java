package hu.bme.aut.freelancer_spring.config;

import hu.bme.aut.freelancer_spring.model.Package;
import hu.bme.aut.freelancer_spring.model.Transfer;
import hu.bme.aut.freelancer_spring.repository.PackageRepository;
import hu.bme.aut.freelancer_spring.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {

    private final TransferRepository transferRepository;
    private final PackageRepository packageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var packages = packageRepository.saveAll(
                Arrays.asList(
                        new Package("elso", 1, 2),
                        new Package("masodik", 10, 210),
                        new Package("drage", 1100, 21)
                )
        );

        var transfers = transferRepository.saveAll(
                Arrays.asList(
                        new Transfer(new Date(), 23.34, 34.546),
                        new Transfer(new Date(), 13.34, 344.546),
                        new Transfer(new Date(), 543.34, 344.546),
                        new Transfer(new Date(), 237.34, 364.546)
                )
        );

        transfers.get(0).addPackage(packages.get(0));
        packages.get(0).setTransfer(transfers.get(0));
        transfers.get(0).addPackage(packages.get(1));
        packages.get(1).setTransfer(transfers.get(0));
        transfers.get(0).addPackage(packages.get(2));
        packages.get(2).setTransfer(transfers.get(0));

        transferRepository.save(transfers.get(0));

        packageRepository.saveAll(packages);
    }
}
