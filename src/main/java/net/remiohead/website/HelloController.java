package net.remiohead.website;

import net.remiohead.website.models.Tables;
import net.remiohead.website.models.tables.Person;
import net.remiohead.website.models.tables.records.PersonRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final DSLContext context;

    @Autowired
    public HelloController(final DSLContext context) {
        this.context = requireNonNull(context);
    }

    @RequestMapping()
    public List<String> index() {
        return this.context
                .selectFrom(Tables.PERSON)
                .fetch()
                .stream()
                .map(PersonRecord::getName)
                .collect(Collectors.toList());
    }
}
