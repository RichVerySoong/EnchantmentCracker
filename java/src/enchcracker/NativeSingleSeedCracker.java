package enchcracker;

import javax.swing.JOptionPane;

public class NativeSingleSeedCracker extends AbstractSingleSeedCracker {

	@Override
	public boolean initCracker() {
		Log.info("System details:");
		Log.info("OS = " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
		Log.info("Arch (either OS/Java) = " + System.getProperty("os.arch"));
		Log.info("Java = " + System.getProperty("java.version"));
		if (System.getProperties().containsKey("sun.arch.data.model")) {
			Log.info("Java arch = " + System.getProperty("sun.arch.data.model"));
		}

		try {
			System.loadLibrary("enchcracker");
		} catch (UnsatisfiedLinkError e) {
			Log.warn("Failed to load native enchcracker library! Using the Java version instead", e);

			String message = "Failed to load native enchcracker library!\n";
			String origMessage = e.getMessage();
			message += "Message: " + origMessage + "\n";
			String probableCause;
			if (origMessage.contains("32") || origMessage.contains("64")) {
				probableCause = "You are using 32-bit Java or a 32-bit system. You need 64-bit.";
			} else if (origMessage.contains("java.library.path")) {
				probableCause = "The library file could not be found.\nEnsure you are launching as described in README.txt";
			} else {
				probableCause = "Unknown";
			}
			message += "Probable cause: " + probableCause + "\n";
			message += "\n";
			message += "If you are sure this is not the problem, create an issue on GitHub.\n";
			message += "Make sure to include the contents of the log file (enchcracker.log) in your bug report.\n";
			message += "\n";
			message += "Press OK to continue with a slower Java implementation of the XP seed finder";
			JOptionPane.showMessageDialog(null, message, "Library load error", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		ninitCracker();
		return true;
	}

	private native void ninitCracker();

	@Override
	public void resetCracker() {
		nresetCracker();
	}

	private native void nresetCracker();

	@Override
	public void firstInput(int bookshelves, int slot1, int slot2, int slot3) {
		nfirstInput(bookshelves, slot1, slot2, slot3);
	}

	private native void nfirstInput(int bookshelves, int slot1, int slot2, int slot3);

	@Override
	public void addInput(int bookshelves, int slot1, int slot2, int slot3) {
		naddInput(bookshelves, slot1, slot2, slot3);
	}

	private native void naddInput(int bookshelves, int slot1, int slot2, int slot3);

	@Override
	public int getPossibleSeeds() {
		return ngetPossibleSeeds();
	}

	private native int ngetPossibleSeeds();

	@Override
	public int getSeed() {
		return ngetSeed();
	}

	private native int ngetSeed();

	@Override
	public void requestAbort() {
		nrequestAbort();
	}

	private native void nrequestAbort();

	@Override
	public boolean isAbortRequested() {
		return nisAbortRequested();
	}

	private native boolean nisAbortRequested();

	@Override
	public long getSeedsSearched() {
		return ngetSeedsSearched();
	}

	private native long ngetSeedsSearched();

}
