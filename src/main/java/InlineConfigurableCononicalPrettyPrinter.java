import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.IOException;

public class InlineConfigurableCononicalPrettyPrinter extends DefaultPrettyPrinter {

    protected Indenter _arrayIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
    protected InlineConfigurableIndenter _objectIndenter = InlineConfigurableIndenter.SYSTEM_LINEFEED_INSTANCE;
    protected Indenter _objectInlineIndenter = new DefaultIndenter("", "");

    public void setObjectInline(boolean isInline) {
        this._objectIndenter.setInline(isInline);
    }

    @Override
    public void writeStartObject(JsonGenerator g) throws IOException {
        g.writeRaw('{');
        if (!_objectIndenter.isInline()) {
            ++_nesting;
        }
    }

    @Override
    public void beforeObjectEntries(JsonGenerator g) throws IOException {
        if (!_objectIndenter.isInline()) {
            _objectIndenter.writeIndentation(g, _nesting);
        } else {
            g.writeRaw(' ');
            _objectInlineIndenter.writeIndentation(g, _nesting);
        }
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
        if (!_objectIndenter.isInline()) {
            if (_spacesInObjectEntries) {
                g.writeRaw(_objectFieldValueSeparatorWithSpaces);
            } else {
                g.writeRaw(_separators.getObjectFieldValueSeparator());
            }
        } else {
            g.writeRaw(_separators.getObjectFieldValueSeparator());
            g.writeRaw(' ');
        }
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator g) throws IOException {
        if (!_objectIndenter.isInline()) {
            g.writeRaw(_separators.getObjectEntrySeparator());
            _objectIndenter.writeIndentation(g, _nesting);
        } else {
            g.writeRaw(_separators.getObjectEntrySeparator());
            g.writeRaw(' ');
        }
    }

    @Override
    public void writeEndObject(JsonGenerator g, int nrOfEntries) throws IOException {
        if (!_objectIndenter.isInline()) {
            --_nesting;

            if (nrOfEntries > 0) {
                _objectIndenter.writeIndentation(g, _nesting);
            } else {
                g.writeRaw(' ');
            }
        } else {
            if (nrOfEntries > 0) {
                _objectInlineIndenter.writeIndentation(g, _nesting);
            } else {
                g.writeRaw(' ');
            }
        }
        g.writeRaw('}');
    }


    @Override
    public void writeStartArray(JsonGenerator g) throws IOException {
        if (!_arrayIndenter.isInline()) {
            ++_nesting;
        }
        g.writeRaw('[');
    }

    @Override
    public void beforeArrayValues(JsonGenerator g) throws IOException {
        _arrayIndenter.writeIndentation(g, _nesting);
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator g) throws IOException {
        g.writeRaw(_separators.getArrayValueSeparator());
        _arrayIndenter.writeIndentation(g, _nesting);
    }

    @Override
    public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
        if (!_arrayIndenter.isInline()) {
            --_nesting;
        }
        if (nrOfValues > 0) {
            _arrayIndenter.writeIndentation(g, _nesting);
        } else {
            g.writeRaw(' ');
        }
        g.writeRaw( ']');
    }
}
