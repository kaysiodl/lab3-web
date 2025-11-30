package ru.kaysiodl.taglib.components.text;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UIOutput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import lombok.extern.java.Log;
import ru.kaysiodl.services.TextCounter;

import java.io.IOException;
import java.io.StringWriter;

@Log
@FacesComponent("counterComponent")
public class Counter extends UIComponentBase {
    private String show;
    private String excludeSpaces;
    private String id;

    @Override
    public String getFamily() {
        return "kaysiodl.components";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        initAttributes();
    }

    @Override
    public void encodeChildren(FacesContext context) throws IOException {}

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        String text = extractText(context);

        TextCounter textCounter = new TextCounter();
        boolean excludeSpaces = "true".equalsIgnoreCase(this.excludeSpaces);
        String show = this.show == null ? "all" : this.show;
        String id = this.id != null ? this.id : String.valueOf(System.currentTimeMillis());

        int chars = textCounter.countCharacters(text, excludeSpaces);
        int lines = textCounter.countLines(text);
        int words = textCounter.countWords(text);
        log.info("Chars: " + chars + " words: " + words + " lines: " + lines);

        renderHTML(writer, text, chars, words, lines, show, id);

        writer.endElement("div");
    }

    public void initAttributes() {
        this.show = (String) getAttributes().get("show");
        this.excludeSpaces = (String) getAttributes().get("excludeSpaces");
        this.id = (String) getAttributes().get("id");
    }

    public String extractText(FacesContext context) throws IOException {
        StringBuilder text = new StringBuilder();

        String value = (String) getAttributes().get("value");
        if (value != null && !value.trim().isEmpty()) {
            text.append(value);
        } else {
            text.append(extractTextFromChildren(context));
        }

        return text.toString().trim();
    }

    private String extractTextFromChildren(FacesContext context) throws IOException {
        StringBuilder text = new StringBuilder();

        for (UIComponent child : getChildren()) {
            if (text.length() > 0) {
                text.append("\n");
            }
            if (child instanceof UIOutput) {
                Object value = ((UIOutput) child).getValue();
                if (value != null) {
                    text.append(value);
                }
            } else {
                String html = renderToString(context, child);
                String cleanText = html.replaceAll("<[^>]+>", "");
                text.append(cleanText);
            }
        }

        return text.toString();
    }

    private String renderToString(FacesContext context, UIComponent component) throws IOException {
        StringWriter sw = new StringWriter();
        ResponseWriter writer = context.getResponseWriter();
        ResponseWriter tempWriter = writer.cloneWithWriter(sw);

        try {
            context.setResponseWriter(tempWriter);
            component.encodeAll(context);
            return sw.toString();
        } finally {
            context.setResponseWriter(writer);
            tempWriter.close();
        }
    }

    protected void renderHTML(ResponseWriter writer,
                              String text,
                              int chars,
                              int words,
                              int lines,
                              String show,
                              String id) throws IOException {
        writer.startElement("div", this);
        writer.writeAttribute("class", "text-counter-container", null);
        writer.writeAttribute("id", id, null);

        writer.startElement("div", this);
        writer.writeAttribute("class", "text-counter-stats", null);

        writer.startElement("div", this);
        writer.writeAttribute("class", "stats-header", null);
        writer.writeText("Статистика текста: ", null);
        writer.endElement("div");

        renderStats(writer, chars, words, lines, show);
        writer.endElement("div");

        writer.startElement("div", this);
        writer.writeAttribute("class", "text-counter-original", null);

        writer.startElement("div", this);
        writer.writeAttribute("class", "original-header", null);
        writer.writeText("Исходный текст:", null);
        writer.endElement("div");

        writer.startElement("pre", this);
        writer.writeAttribute("class", "text-counter-body", null);
        writer.writeText(text, null);
        writer.endElement("pre");

        writer.endElement("div");
    }

    protected void renderStats(ResponseWriter writer,
                               int chars,
                               int words,
                               int lines,
                               String show) throws IOException {
        switch (show) {
            case "chars": renderStatItem(writer, "Символов: " + chars); break;
            case "words": renderStatItem(writer, "Слов: " + words); break;
            case "lines": renderStatItem(writer, "Строк: " + lines); break;
            default:
                renderStatItem(writer, "Символов: " + chars);
                renderStatItem(writer, "Слов: " + words);
                renderStatItem(writer, "Строк: " + lines);
                break;
        }
    }

    protected void renderStatItem(ResponseWriter writer, String text) throws IOException {
        writer.startElement("div", this);
        writer.writeAttribute("class", "stat-item", null);
        writer.writeText(text, null);
        writer.endElement("div");
    }
}