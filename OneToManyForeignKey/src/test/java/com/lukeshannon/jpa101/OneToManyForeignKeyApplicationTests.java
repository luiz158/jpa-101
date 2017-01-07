package com.lukeshannon.jpa101;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukeshannon.jpa101.model.SuperAlias;
import com.lukeshannon.jpa101.model.SuperHero;
import com.lukeshannon.jpa101.repo.SuperAliasRepo;
import com.lukeshannon.jpa101.repo.SuperHeroRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneToManyForeignKeyApplicationTests {
	
	@Autowired
	private SuperHeroRepo heroRepo;
	
	@Autowired
	private SuperAliasRepo aliasRepo;
	
	private Long idOfFlash;
	
	private static int testNumber = 0;

	@Test
	public void contextLoads() {
	}
	
	@Before
	@Sql({"/schema.sql"})
	public void loadData() {
			System.out.println("(BEFORE - Start) Start Writing Clean Objects For Test Heroes: " + heroRepo.count() + " Alias: " + aliasRepo.count() + " On Test Numbers: " + ++testNumber);
			SuperHero flash = new SuperHero();
			flash.setName("Barry Allen");
			SuperAlias alias1 = new SuperAlias("Scarlet Speedster", new Date(), new Date());
			alias1.setSuperHero(flash);
			SuperAlias alias2 = new SuperAlias("Flash", new Date(), new Date());
			alias2.setSuperHero(flash);
			List<SuperAlias> aliases = new ArrayList<SuperAlias>();
			aliases.add(alias1);
			aliases.add(alias2);
			flash.setAliases(aliases);
			idOfFlash = heroRepo.save(flash).getId();
			SuperHero spiderman = new SuperHero();
			spiderman.setName("Peter Parker");
			alias1 = new SuperAlias("Wall Crawler", new Date(), new Date());
			alias1.setSuperHero(spiderman);
			alias2 = new SuperAlias("Spider-man", new Date(), new Date());
			alias2.setSuperHero(spiderman);
			aliases = new ArrayList<SuperAlias>();
			aliases.add(alias1);
			aliases.add(alias2);
			spiderman.setAliases(aliases);
			heroRepo.save(spiderman);
			System.out.println("(BEFORE -End) Ending Writing Clean Objects For Test. Heroes: " + heroRepo.count() + " Alias: " + aliasRepo.count());
			System.out.println("");
	}
	
	@After
	public void cleanOutDB() {
		System.out.println("(AFTER - Start) Deleting All the Heroes (should remove Alias) Heroes: " + heroRepo.count() + " Alias: " + aliasRepo.count());
		Iterable<SuperHero> heroes = heroRepo.findAll();
		for (SuperHero hero : heroes) {
			heroRepo.delete(hero);
		}
		System.out.println("(AFTER) Hero Count (just deleted Heroes): " + heroRepo.count());
		assertTrue(heroRepo.count() == 0);
		System.out.println("(AFTER) Alias Count (just deleted Heroes): " + aliasRepo.count());
		assertTrue(aliasRepo.count() == 0);
		System.out.println("(AFTER - End) On Test Numbers: " + testNumber );
		System.out.println("");
	}
	
	@Test
	public void testWrites() {
		System.out.println("TEST WRITES: Heroes: " + heroRepo.count());
		assertTrue(heroRepo.count() == 2);
		int associatedAlias = 0;
		Iterable<SuperHero> heroes = heroRepo.findAll();
		for (SuperHero hero : heroes) {
			associatedAlias = associatedAlias + hero.getAliases().size();
			assertNotNull(hero);
			assertNotNull(hero.getAliases().size() == 2);
			for (SuperAlias alias : hero.getAliases()) {
				assertNotNull(alias);
			}
		}
		System.out.println("TEST WRITES: Associated Alias: " + associatedAlias + " Total Alias in DB: " + aliasRepo.count());
		assertTrue(associatedAlias == aliasRepo.count());
		System.out.println("");
	}
	
	@Test
	@Commit
	public void testUpdate() {
		SuperHero hero = heroRepo.findOne(idOfFlash);
		System.out.println("TEST UPDATE: Before the Update here is the Flash " + hero.toString());
		assertNotNull(hero);
		hero.getAliases().clear();
		SuperAlias alias = new SuperAlias("Parallax", new Date(), new Date());
		alias.setSuperHero(hero);
		hero.getAliases().add(alias);
		heroRepo.save(hero);
		System.out.println("After Updating the Flash (to have only one Alias), here are all the Alias in the DB (how many for the Flash?):");
		for (SuperAlias db_alias : aliasRepo.findAll()) {
			System.out.println(db_alias);
		}
		System.out.println("After deleting the old aliases, here are all the Alias in the DB (how many for the Flash?):");
		for (SuperAlias db_alias : aliasRepo.findAll()) {
			System.out.println(db_alias);
		}
		System.out.println("TEST UPDATE: After Update here is the Flash (his Alias list was overridden): " +  heroRepo.findOne(idOfFlash));
		assertTrue(heroRepo.count() == 2);
		assertTrue(heroRepo.findOne(hero.getId()).getAliases().size() == 1);
		System.out.println("");
	}
	
	@Test
	public void testDelete() {
		int original_size = 0;
		System.out.println("TEST DELETE: Here are all the Alias before deleting the Flash (Crisis On Infinite Earth Has Not Happened): ");
		for(SuperAlias alias : aliasRepo.findAll()) {
			System.out.println(alias);
			original_size++;
		}
		SuperHero hero = heroRepo.findOne(idOfFlash);
		System.out.println("TEST DELETE: Alias Count before the delete: " + aliasRepo.count());
		System.out.println("TEST DELETE: Aliases the Flash has: " + hero.getAliases().size());
		int flash_count = hero.getAliases().size();
		heroRepo.delete(hero);
		assertTrue(heroRepo.findByName("Barry Allen").size() == 0);
		int new_size = 0;
		System.out.println("TEST DELETE: Here are all the Alias after deleting the Flash (Crisis On Infinite Earth Has Happened): ");
		for(SuperAlias alias : aliasRepo.findAll()) {
			System.out.println(alias.toString());
			new_size++;
		}
		System.out.println("TEST DELETE: Expecting " + (original_size - flash_count) + " as the alias count after deleting the flash. Current count is actually: " + new_size);
		assertTrue(new_size == (original_size - flash_count));
		System.out.println("");
	}
	
	@Test
	public void testGetAlias() {
		System.out.println("TEST GET ALIAS: Expecting 4 alias and got back: " + aliasRepo.count());
		Iterable<SuperAlias> aliases = aliasRepo.findAll();
		assertNotNull(aliases);
		assertTrue(aliasRepo.count() == 4);
		System.out.println("");
	}

}
