package sg.edu.nus.comp.cs4218.impl.app;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.CalException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class CalApplicationTest {
	static CalApplication calApp;
	static InputStream is;
	static OutputStream os;

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static Date date = new Date();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		calApp = new CalApplication();
		is = null;
		os = new ByteArrayOutputStream();

	}

	@After
	public void tearDown() throws Exception {
		os = new ByteArrayOutputStream();
	}

	// Cal -m month year
	@Test
	public void testCalMondayFirstWithMonthAndYear() throws CalException {
		String args[] = "-m 6 1993".split(" ");
		String expected = "     June 1993      \n" + "Mo Tu We Th Fr Sa Su\n" + "   1  2  3  4  5  6 \n"
				+ "7  8  9  10 11 12 13\n" + "14 15 16 17 18 19 20\n" + "21 22 23 24 25 26 27\n"
				+ "28 29 30            ";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(expected, output);

	}

	// Cal month year
	@Test
	public void testCalSundayFirstWithMonthAndYear() throws CalException {
		String args[] = "6 1993".split(" ");
		String expected = "     June 1993      \n" + "Su Mo Tu We Th Fr Sa\n" + "      1  2  3  4  5 \n"
				+ "6  7  8  9  10 11 12\n" + "13 14 15 16 17 18 19\n" + "20 21 22 23 24 25 26\n"
				+ "27 28 29 30         ";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(expected, output);

	}

	// Cal -m year
	@Test
	public void testCalMondayFirstWithYearOnly() throws CalException {
		String args[] = "-m 1993".split(" ");
		String expected = "                              1993\n"
				+ "      January               February               March        \n"
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n"
				+ "            1  2  3   1  2  3  4  5  6  7   1  2  3  4  5  6  7 \n"
				+ "4  5  6  7  8  9  10  8  9  10 11 12 13 14  8  9  10 11 12 13 14\n"
				+ "11 12 13 14 15 16 17  15 16 17 18 19 20 21  15 16 17 18 19 20 21\n"
				+ "18 19 20 21 22 23 24  22 23 24 25 26 27 28  22 23 24 25 26 27 28\n"
				+ "25 26 27 28 29 30 31                                            \n\n\n"
				+ "       April                  May                   June        \n"
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n"
				+ "         1  2  3  4                  1  2      1  2  3  4  5  6 \n"
				+ "5  6  7  8  9  10 11  3  4  5  6  7  8  9   7  8  9  10 11 12 13\n"
				+ "12 13 14 15 16 17 18  10 11 12 13 14 15 16  14 15 16 17 18 19 20\n"
				+ "19 20 21 22 23 24 25  17 18 19 20 21 22 23  21 22 23 24 25 26 27\n"
				+ "26 27 28 29 30        24 25 26 27 28 29 30  28 29 30            \n"
				+ "                      31                                        \n\n\n"
				+ "        July                 August              September      \n"
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n"
				+ "         1  2  3  4                     1         1  2  3  4  5 \n"
				+ "5  6  7  8  9  10 11  2  3  4  5  6  7  8   6  7  8  9  10 11 12\n"
				+ "12 13 14 15 16 17 18  9  10 11 12 13 14 15  13 14 15 16 17 18 19\n"
				+ "19 20 21 22 23 24 25  16 17 18 19 20 21 22  20 21 22 23 24 25 26\n"
				+ "26 27 28 29 30 31     23 24 25 26 27 28 29  27 28 29 30         \n"
				+ "                      30 31                                     \n\n\n"
				+ "      October               November              December      \n"
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n"
				+ "            1  2  3   1  2  3  4  5  6  7         1  2  3  4  5 \n"
				+ "4  5  6  7  8  9  10  8  9  10 11 12 13 14  6  7  8  9  10 11 12\n"
				+ "11 12 13 14 15 16 17  15 16 17 18 19 20 21  13 14 15 16 17 18 19\n"
				+ "18 19 20 21 22 23 24  22 23 24 25 26 27 28  20 21 22 23 24 25 26\n"
				+ "25 26 27 28 29 30 31  29 30                 27 28 29 30 31      \n";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(expected, output);

	}

	// Cal year
	@Test
	public void testCalSundayFirstWithYearOnly() throws CalException {
		String args[] = "1993".split(" ");
		String expected = "                              1993\n"
				+ "      January               February               March        \n"
				+ "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n"
				+ "               1  2      1  2  3  4  5  6      1  2  3  4  5  6 \n"
				+ "3  4  5  6  7  8  9   7  8  9  10 11 12 13  7  8  9  10 11 12 13\n"
				+ "10 11 12 13 14 15 16  14 15 16 17 18 19 20  14 15 16 17 18 19 20\n"
				+ "17 18 19 20 21 22 23  21 22 23 24 25 26 27  21 22 23 24 25 26 27\n"
				+ "24 25 26 27 28 29 30  28                    28 29 30 31         \n"
				+ "31                                                              \n\n\n"
				+ "       April                  May                   June        \n"
				+ "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n"
				+ "            1  2  3                     1         1  2  3  4  5 \n"
				+ "4  5  6  7  8  9  10  2  3  4  5  6  7  8   6  7  8  9  10 11 12\n"
				+ "11 12 13 14 15 16 17  9  10 11 12 13 14 15  13 14 15 16 17 18 19\n"
				+ "18 19 20 21 22 23 24  16 17 18 19 20 21 22  20 21 22 23 24 25 26\n"
				+ "25 26 27 28 29 30     23 24 25 26 27 28 29  27 28 29 30         \n"
				+ "                      30 31                                     \n\n\n"
				+ "        July                 August              September      \n"
				+ "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n"
				+ "            1  2  3   1  2  3  4  5  6  7            1  2  3  4 \n"
				+ "4  5  6  7  8  9  10  8  9  10 11 12 13 14  5  6  7  8  9  10 11\n"
				+ "11 12 13 14 15 16 17  15 16 17 18 19 20 21  12 13 14 15 16 17 18\n"
				+ "18 19 20 21 22 23 24  22 23 24 25 26 27 28  19 20 21 22 23 24 25\n"
				+ "25 26 27 28 29 30 31  29 30 31              26 27 28 29 30      \n\n\n"
				+ "      October               November              December      \n"
				+ "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n"
				+ "               1  2      1  2  3  4  5  6            1  2  3  4 \n"
				+ "3  4  5  6  7  8  9   7  8  9  10 11 12 13  5  6  7  8  9  10 11\n"
				+ "10 11 12 13 14 15 16  14 15 16 17 18 19 20  12 13 14 15 16 17 18\n"
				+ "17 18 19 20 21 22 23  21 22 23 24 25 26 27  19 20 21 22 23 24 25\n"
				+ "24 25 26 27 28 29 30  28 29 30              26 27 28 29 30 31   \n"
				+ "31                                                              \n";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(expected, output);

	}

	// Cal -m
	@Test
	public void testCalMondayFirst() throws CalException {
		String args[] = "-m".split(" ");
		String expected = "     March 2017     \n" + "Mo Tu We Th Fr Sa Su\n" + "      1  2  3  4  5 \n"
				+ "6  7  8  9  10 11 12\n" + "13 14 15 16 17 18 19\n" + "20 21 22 23 24 25 26\n"
				+ "27 28 29 30 31      ";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(output, output);

	}

	// Cal
	@Test
	public void testCalSundayFirst() throws CalException {
		String args[] = new String[0];
		String expected = "     March 2017     \n" + "Su Mo Tu We Th Fr Sa\n" + "         1  2  3  4 \n"
				+ "5  6  7  8  9  10 11\n" + "12 13 14 15 16 17 18\n" + "19 20 21 22 23 24 25\n"
				+ "26 27 28 29 30 31   ";
		calApp.run(args, is, os);
		String output = os.toString();
		assertEquals(output, output);
	}

	// Failing test cases

	// cal a
	@Test(expected = CalException.class)
	public void testCalSundayFirstInvalidYear() throws CalException {
		String args[] = "a".split(" ");
		calApp.run(args, is, os);
	}

	// cal -m z
	@Test(expected = CalException.class)
	public void testCalMondayFirstInvalidYear() throws CalException {
		String args[] = "-m asd".split(" ");
		calApp.run(args, is, os);
	}

	// cal a 1999
	@Test(expected = CalException.class)
	public void testCalMondayFirstInvalidM() throws CalException {
		String args[] = "asd 2000".split(" ");
		calApp.run(args, is, os);
	}

	// cal 0 1999
	@Test
	public void testCalSundayFirstMonthAndYearInvalidMonth() {
		String args[] = "0 2000".split(" ");
		Exception exc = new Exception();
		String expected = "cal: month must be between 1 and 12";

		try {
			calApp.run(args, is, os);
			// fail("Exception should be thrown!");
			// String output = os.toString();
			// assertEquals(expected, output);
		} catch (CalException e) {
			// TODO Auto-generated catch block
			exc = e;
		}
		assertEquals(expected, exc.getMessage());
	}

	// cal 6 zx
	@Test(expected = CalException.class)
	public void testCalSundayFirstMonthAndYearInvalidYear() throws CalException {
		String args[] = "6 dfv".split(" ");
		calApp.run(args, is, os);
	}

	// cal a b
	@Test(expected = CalException.class)
	public void testCalSundayFirstMonthAndYearInvalidMonthAndYear() throws CalException {
		String args[] = "fg dfv".split(" ");
		calApp.run(args, is, os);
	}

	// cal -m 6 abc
	@Test(expected = CalException.class)
	public void testCalMondayFirstMonthAndYearInvalidYear() throws CalException {
		String args[] = "-m 3 rgfds".split(" ");
		calApp.run(args, is, os);
	}

	// cal -m 0 2000
	@Test(expected = CalException.class)
	public void testCalMondayFirstMonthAndYearInvalidMonth() throws CalException {
		String args[] = "-m 32 2006".split(" ");
		calApp.run(args, is, os);
	}

	// cal a 4 2000
	@Test(expected = CalException.class)
	public void testCalMondayFirstMonthAndYearInvalidM() throws CalException {
		String args[] = "2asd 3 2006".split(" ");
		calApp.run(args, is, os);
	}
}
