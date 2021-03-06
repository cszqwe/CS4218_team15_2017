package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

/*
 * Assumptions: 
 * 1) run function will call the correct functions with the correct inputs in the correct order separated by a space
 * 2) Run function will take inputs directly from shell unordered
 * 3) Args for run: unordered consisting of pattern and files
 * 4) Args for grepFromOneFile: pattern, file
 * 5) Args for grepFromMultipleFiles: pattern, file, file, ...
 * 6) Args for grepFromStdin: pattern (Stdin will be parsed from run)
 */

public class ExtraGrepApplicationTest {
	private static final String REGEXMULTIOUT = "Hello Hello\nABC Hello\nello milo";
	private static final String NOMATCHFILE = "Pattern Not Found In File!";
	private static final String REGEXPATTERNOUT = "Hello Hello\nABC Hello";
	private static final String REGEXPATTERN = ".*ell";
	private static final String ABCSINGLEFILEOUT = "ABC Hello\nABCDEFGHI";
	private static final String ABCPATTERN = "ABC";
	private static final String HIEPATTERN = "hie";
	private static final String NOMATCHSTDIN = "Pattern Not Found In Stdin!";
	private GrepApplication grepApp;
	private String[] args;
	private FileInputStream stdin;
	private FileInputStream failstdin;

	static ShellImpl shell;
	static OutputStream os;

	private String fileName;
	private String fileName2;
	private String fileName3;
	private String directory;
	private String invalidFile;
	private ByteArrayOutputStream baos;
	PrintStream print;

	@Before
	public void setUp() throws FileNotFoundException {
		shell = new ShellImpl();
		grepApp = new GrepApplication();
		stdin = new FileInputStream("test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc.txt");
		failstdin = new FileInputStream("test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc.txt");

		fileName = "test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc.txt";
		fileName2 = "test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc2.txt";
		fileName3 = "test/sg/edu/nus/comp/cs4218/impl/app/testdoc.txt";
		invalidFile = "test/sg/edu/nus/comp/cs4218/impl/app/abjkcsnakjc.txt";
		directory = "test/sg/edu/nus/comp/cs4218/impl/app/";
		baos = new ByteArrayOutputStream();
		print = new PrintStream(baos);
		System.setOut(print);
	}

	@Test(expected = GrepException.class)
	public void grepStdInNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = HIEPATTERN;
		grepApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(NOMATCHSTDIN + "\n", baos.toString());
	}

	@Test
	public void grepStdInMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = ABCPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + "\n", baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepStdInInvalidPattern() throws AbstractApplicationException {
		args = new String[1];
		args[0] = "[";
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + "\n", baos.toString());
	}

	@Test
	public void grepStdInValidPatternNoMatch() throws AbstractApplicationException {
		args = new String[1];
		args[0] = "asdasda";
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepStdInValidStdin() throws AbstractApplicationException, IOException {
		args = new String[1];
		args[0] = "asdasda";
		failstdin.close();
		grepApp.run(args, failstdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepStdInInvalidOutputStream() throws AbstractApplicationException {
		args = new String[1];
		args[0] = ABCPATTERN;
		grepApp.run(args, stdin, null);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + "\n", baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepStdInInvalidPatternInvalidOutputStream() throws AbstractApplicationException {
		args = new String[1];
		args[0] = "[";
		grepApp.run(args, stdin, null);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + "\n", baos.toString());
	}

	@Test
	public void grepStdInRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + "\n", baos.toString());
	}

	@Test
	public void grepSingleFileNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test
	public void grepSingleFileMultipleMatchesInALineFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = "h";
		args[1] = fileName3;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("Boisterous he on understood attachment as entreaties ye devonshire.\n"
				+ "Extremely ham any his departure for contained curiosity defective.\n"
				+ "Way now instrument had eat diminution melancholy expression sentiments stimulated.\n"
				+ "Mrs interested now his affronting inquietude contrasted cultivated.\n"
				+ "Lasting showing expense greater on colonel no.\n"
				+ "Prepared do an dissuade be so whatever steepest.\n"
				+ "Yet her beyond looked either day wished nay.\n"
				+ "Now curiosity you explained immediate why behaviour.\n"
				+ "An dispatched impossible of of melancholy favourable.\n"
				+ "Our quiet not heart along scale sense timed.\n"
				+ "Consider may dwelling old him her surprise finished families graceful.\n"
				+ "Is at purse tried jokes china ready decay an.\n" + "Small its shy way had woody downs power.\n"
				+ "Procured shutters mr it feelings.\n" + "To or three offer house begin taken am at.\n"
				+ "As dissuade cheerful overcame so of friendly he indulged unpacked.\n"
				+ "An seeing feebly stairs am branch income me unable.\n"
				+ "Celebrated contrasted discretion him sympathize her collecting occasional.\n"
				+ "Do answered bachelor occasion in of offended no concerns.\n"
				+ "Supply worthy warmth branch of no ye.\n" + "Though wished merits or be.\n"
				+ "Alone visit use these smart rooms ham.\n" + "Course sir people worthy horses add entire suffer.\n"
				+ "Strictly numerous outlived kindness whatever on we no on addition.\n"
				+ "Are sentiments apartments decisively the especially alteration.\n"
				+ "Thrown shy denote ten ladies though ask saw.\n" + "Or by to he going think order event music.\n"
				+ "Led income months itself and houses you. After nor you leave might share court balls.\n",
				baos.toString());
	}

	@Test
	public void grepSingleFileMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = ABCPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + "\n", baos.toString());
	}

	@Test
	public void grepSingleFileRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + "\n", baos.toString());
	}

	@Test
	public void grepMultipleFileNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test
	public void grepMultipleFileMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "DEF";
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("ABCDEFGHI\nDEF" + "\n", baos.toString());
	}

	@Test
	public void grepMultipleFileRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + "\n", baos.toString());
	}

	@Test
	public void grepUnorderedInput1FromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = fileName;
		args[1] = REGEXPATTERN;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("grep: .*ell: No such file\n", baos.toString());
	}

	@Test
	public void grepUnorderedInput2FromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = fileName;
		args[1] = fileName2;
		args[2] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("grep: .*ell: No such file\n", baos.toString());
	}

	@Test
	public void grepMultiFileDirectoryFromRun() throws AbstractApplicationException {
		args = new String[4];
		args[1] = fileName;
		args[2] = fileName2;
		args[0] = REGEXPATTERN;
		args[3] = directory;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + "\n" + "grep: test/sg/edu/nus/comp/cs4218/impl/app/: No such file\n",
				baos.toString());
	}

	@Test
	public void grepDirectoryWithFileFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		args[2] = directory;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + "\n" + "grep: test/sg/edu/nus/comp/cs4218/impl/app/: No such file\n",
				baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepnoStdinFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = fileName;
		grepApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test
	public void grepNoPatternMultipleFileFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = fileName;
		args[1] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void grepNoPatternStdInFromRun() throws AbstractApplicationException {
		args = new String[0];
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void grepArgsFromRun() throws AbstractApplicationException {
		args = new String[0];
		grepApp.run(null, stdin, System.out);
		System.out.flush();
	}

	@Test
	public void grepDirectoryFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = REGEXPATTERN;
		args[1] = directory;
		grepApp.run(args, stdin, System.out);
		assertEquals(baos.toString(), baos.toString());
	}

	@Test(expected = GrepException.class)
	public void invalidRegexFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "[";
		args[1] = fileName2;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void invalidRegexFileFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "[";
		args[1] = fileName2;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	// Integration tests

	@Test
	// Test the case of calling command functions
	public void commandSubTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line` test.txt";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of calling command functions
	public void commandSubTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line | head` test.txt";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = CatException.class)
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void commandSubTestFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line | cat test5.txt` test.txt";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of pipe
	public void pipeTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "sed s/[l]/L/g test.txt | grep Line";
		String expected = "Line 1\nLine 2\nLine 3\nLine 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of pipe
	public void pipeTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "head -n 2 test.txt | grep line";
		String expected = "line 1\nline 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of pipe
	public void pipeTest3() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "date | grep T";
		String expected = java.util.Calendar.getInstance().getTime().toString() + "\n"; // assumption:
																						// tester
																						// is
																						// doing
																						// this
																						// in
																						// Singapore
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = HeadException.class)
	// Test the case of pipe with exception
	public void pipeTestFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "head -n 2 tesasdt.txt | grep line";
		String expected = "line 1\nline 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of calling command functions
	public void complicatedCommandSubTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line` `echo test.txt`";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of calling command functions
	public void complicatedCommandSubTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line | head` `echo test.txt`";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = SortException.class)
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void ComplicatedommandSubTestFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line | cat test.txt` `sort test5.txt`";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of calling command functions
	public void complicatedPipeTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep line test.txt | tail -n 2 | grep 'line 4'";
		String expected = "line 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the case of calling command functions
	public void complicatedPipeSubTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt | grep 'line' | grep 'li'";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = GrepException.class)
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void ComplicatedPipeTestFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep `echo line | cat test.txt` `sort test.txt` | grep | grep line2";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void quoteTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep line 2 test.txt";
		String expected = "grep: 2: No such file\nline 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void quoteTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep \"line 2\" test.txt";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void quoteTest3() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = GrepException.class)
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void quoteFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "'grep' 'line 2' test.txt | 'grep' [";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void redirTest1() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt > line2.txt";
		String cmdline2 = "grep line < line2.txt";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		os = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdline2, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void redirTest2() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt > line2.txt";
		String cmdline2 = "cat test.txt | grep line < line2.txt";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		os = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdline2, os);
		assertEquals(expected, os.toString());
	}

	@Test
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void redirTest3() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt > line2.txt";
		String cmdline2 = "cat test.txt | grep line test.txt < line2.txt";
		String expected = "line 1\nline 2\nline 3\nline 4\n";
		shell.parseAndEvaluate(cmdline, os);
		os = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdline2, os);
		assertEquals(expected, os.toString());
	}

	@Test(expected = ShellException.class)
	// Test the fail case of calling command functions, when command
	// subsititution failed, the whole thing would generate an exception
	public void redirTestFail() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		String cmdline = "grep 'line 2' test.txt > line2.txt";
		String cmdline2 = "grep line < line5.txt";
		String expected = "line 2\n";
		shell.parseAndEvaluate(cmdline, os);
		os = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdline2, os);
		assertEquals(expected, os.toString());
	}
}
