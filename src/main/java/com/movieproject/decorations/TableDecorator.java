package com.movieproject.decorations;

import de.vandermeer.asciitable.AsciiTable;

public class TableDecorator {

    private static TableDecorator instance;

    private TableDecorator() {}

    public static TableDecorator getInstance()
    {
        if (instance == null) instance = new TableDecorator();
        return instance;
    }

    public AsciiTable createTable()
    {
        return new AsciiTable();
    }

    public void add(AsciiTable table, String... columns)
    {
        table.addRule();
        table.addRow(columns);
    }

    public void render(AsciiTable table)
    {
        table.addRule();
        System.out.println(table.render());
    }


}
