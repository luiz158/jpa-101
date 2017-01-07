package com.lukeshannon.jpa101;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukeshannon.jpa101.model.SuperAlias;
import com.lukeshannon.jpa101.model.SuperHero;
import com.lukeshannon.jpa101.repo.SecretAliasRepo;
import com.lukeshannon.jpa101.repo.SecretHeroRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneToManyApplicationTests {
	
	@Autowired
	SecretHeroRepo heroRepo;
	
	@Autowired
	SecretAliasRepo aliasRepo;
	
	private static boolean preLoadComplete = false;
	
	static Long idOfFlash;

	@Test
	public void contextLoads() {
	}
	
	@Before
	public void loadData() {
		
		if (!preLoadComplete) {
			System.out.println("Checking the DB for records (test just started):" + heroRepo.count());
			if (heroRepo.count() > 0) {
				System.out.println("What the fuck...");
				Iterable<SuperHero> heroes = heroRepo.findAll();
				for (SuperHero hero : heroes) {
					System.out.println("Deleting: " + hero);
					heroRepo.delete(hero);
				}
			}
			System.out.println("Starting Writing the Objects");
			SuperHero flash = new SuperHero();
			flash.setName("Barry Allen");
			SuperAlias alias1 = new SuperAlias("Scarlet Speedster", new Date(), new Date());
			SuperAlias alias2 = new SuperAlias("Flash", new Date(), new Date());
			aliasRepo.save(alias1);
			aliasRepo.save(alias2);
			List<SuperAlias> aliases = new ArrayList<SuperAlias>();
			aliases.add(alias1);
			aliases.add(alias2);
			flash.setAliases(aliases);
			idOfFlash = heroRepo.save(flash).getId();
			System.out.println("ID of the Flash is " + idOfFlash);
			
			SuperHero spiderman = new SuperHero();
			spiderman.setName("Peter Parker");
			alias1 = new SuperAlias("Wall Crawler", new Date(), new Date());
			alias2 = new SuperAlias("Spider-man", new Date(), new Date());
			aliasRepo.save(alias1);
			aliasRepo.save(alias2);
			aliases = new ArrayList<SuperAlias>();
			aliases.add(alias1);
			aliases.add(alias2);
			spiderman.setAliases(aliases);
			heroRepo.save(spiderman);
			preLoadComplete = true;
			System.out.println("Ending Writing the Objects");
		}
		
	}
	
	@Test
	public void testWrites() {
		System.out.println(heroRepo.count());
		assertTrue(heroRepo.count() == 2);
		Iterable<SuperHero> heroes = heroRepo.findAll();
		for (SuperHero hero : heroes) {
			assertNotNull(hero);
			assertNotNull(hero.getAliases().size() == 2);
			for (SuperAlias alias : hero.getAliases()) {
				assertNotNull(alias);
			}
		}
	}
	
	@Test
	public void testUpdate() {
		SuperHero hero = heroRepo.findByName("Barry Allen").get(0);
		System.out.println("Before the Update");
		assertNotNull(hero);
		List<SuperAlias> aliases = new ArrayList<SuperAlias>();
		SuperAlias alias = new SuperAlias("Parallax", new Date(), new Date());
		aliases.add(alias);
		hero.setAliases(aliases);
		heroRepo.save(hero);
		assertTrue(heroRepo.count() == 2);
		assertTrue(heroRepo.findOne(hero.getId()).getAliases().size() == 1);
	}

}
