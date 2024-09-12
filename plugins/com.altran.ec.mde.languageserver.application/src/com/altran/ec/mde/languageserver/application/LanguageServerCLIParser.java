package com.altran.ec.mde.languageserver.application;

import java.util.function.Predicate;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.glsp.server.launch.CLIParser;
import org.eclipse.glsp.server.utils.LaunchUtil;
import org.eclipse.glsp.server.utils.LaunchUtil.DefaultOptions;

public class LanguageServerCLIParser extends CLIParser {
	public static final int DEFAULT_LSP_SERVER_PORT = 5008;

	public static final String OPTION_HELP = "help";
	public static final String OPTION_LSP_PORT = "lspPort";
	public static final String OPTION_GLSP_PORT = "glspPort";
	public static final String OPTION_TRACE = "trace";
	public static final String OPTION_WEBSOCKET = "web";

	public LanguageServerCLIParser(final String[] args, final String processName) throws ParseException {
		this(args, getDefaultOptions(), processName);
	}

	public LanguageServerCLIParser(final String[] args, final Options options, final String processName)
			throws ParseException {
		super(args, options, processName);
	}

	public int parseLspPort() {
		Predicate<Integer> validator = (port) -> LaunchUtil.isValidPort(port);
		return parseIntOption(OPTION_LSP_PORT, DEFAULT_LSP_SERVER_PORT, validator);
	}

	public int parseGlspPort() {
		Predicate<Integer> validator = (port) -> LaunchUtil.isValidPort(port);
		return parseIntOption(OPTION_GLSP_PORT, DefaultOptions.SERVER_PORT, validator);
	}

	public boolean isTrace() {
		return hasOption(OPTION_TRACE);
	}

	public boolean isWebSocket() {
		return hasOption(OPTION_WEBSOCKET);
	}

	public boolean isHelp() {
		return hasOption(OPTION_HELP);
	}

	public static Options getDefaultOptions() {
		Options options = new Options();
		options.addOption(OPTION_HELP, false, "Display usage information about language server application");
		options.addOption(OPTION_LSP_PORT, true,
				String.format("Set LSP server port. [default='%s']", DEFAULT_LSP_SERVER_PORT));
		options.addOption(OPTION_GLSP_PORT, true,
				String.format("Set GLSP server port. [default='%s']", DefaultOptions.SERVER_PORT));
		options.addOption(OPTION_TRACE, false, String.format("Enable console message tracing."));
		options.addOption(OPTION_WEBSOCKET, false, String.format("Use websockets instead of plain sockets."));
		return options;
	}
}
