package com.s5.pharmacie_backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.s5.pharmacie_backoffice.models.Medicament;

@SpringBootTest
class PharmacieBackofficeApplicationTests {

	@Test
	void contextLoads() {
		Medicament medicament=new Medicament();
		medicament.setIdMedicament((long) 1);
		System.out.println(medicament.getIdMedicament());
	}

}
