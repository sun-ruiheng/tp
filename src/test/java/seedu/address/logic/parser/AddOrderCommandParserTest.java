package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Date;

public class AddOrderCommandParserTest {
    private static final String NON_EMPTY_DATE = "2020-01-01";
    private static final String NON_EMPTY_DESCRIPTION = "100 chicken wings";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_DATE + NON_EMPTY_DATE + " "
                + PREFIX_DESCRIPTION + NON_EMPTY_DESCRIPTION;

        AddOrderCommand expectedCommand = new AddOrderCommand(INDEX_FIRST_PERSON, new Date(NON_EMPTY_DATE),
                NON_EMPTY_DESCRIPTION);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        String userInput = PREFIX_DATE + NON_EMPTY_DATE + " " + PREFIX_DESCRIPTION + NON_EMPTY_DESCRIPTION;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertThrows(ParseException.class, () -> parser.parse("1"));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_missingDateField_failure() {
        // no parameters
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD, MESSAGE_INVALID_FORMAT);

        // no index
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + " " + PREFIX_DATE + NON_EMPTY_DATE,
                MESSAGE_INVALID_FORMAT);
    }
}
