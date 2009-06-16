package no.java.ems.server.domain;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fj.data.Option;

/**
 * @author <a href="mailto:yngvars@gmail.no">Yngvar S&oslash;rensen</a>
 */
public class Session extends AbstractEntity implements Comparable<Session>, Iterable<Speaker> {
    public enum State {
        Pending,
        Approved,
        Rejected,
    }

    public enum Level {
        Introductory,
        Introductory_Intermediate,
        Intermediate,
        Intermediate_Advanced,
        Advanced,
    }

    public enum Format {
        Presentation,
        BoF,
        PanelDebate,
        Quickie,
        Course,
    }

    private String eventId;
    private Option<Interval> timeslot;
    private State state = State.Pending;
    private Format format = Format.Presentation;
    private Room room;
    private Level level = Level.Intermediate;
    private String title;
    private String lead;
    private String body;
    private Language language;
    private List<String> keywords = new ArrayList<String>();
    private List<Speaker> speakers = new ArrayList<Speaker>();
    private boolean published;
    /***
     * Fields wanted by the SubmitIT gang.
     */
    private String expectedAudience;
    private String outline;
    private String equipment;
    private String feedback;

    public Session() {
    }

    public Session(final String title) {
        this.title = title;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(final String eventId) {
        Validate.notNull(eventId);
        this.eventId = eventId;
    }

    public Option<Interval> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Option<Interval> timeslot) {
        this.timeslot = timeslot;
    }

    public State getState() {
        return state;
    }

    /**
     * @param state session state. May not be {@code null}.
     * @throws IllegalArgumentException if state is {@code null}.
     */
    public void setState(final State state) {
        Validate.notNull(state, "State may not be null");
        this.state = state;
    }

    public Format getFormat() {
        return format;
    }

    /**
     * @param format session format. May not be {@code null}.
     * @throws IllegalArgumentException if format is {@code null}.
     */
    public void setFormat(final Format format) {
        Validate.notNull(format, "Format may not be null");
        this.format = format;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Level getLevel() {
        return level;
    }

    /**
     * @param level session level. May not be {@code null}.
     * @throws IllegalArgumentException if level is {@code null}.
     */
    public void setLevel(final Level level) {
        Validate.notNull(level, "Level may not be null");
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(final String lead) {
        this.lead = lead;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    public void addKeywords(final List<String> keywords) {
        ArrayList<String> allKeywords = new ArrayList<String>(this.keywords);
        for (String keyword : keywords) {
            if (!allKeywords.contains(keyword)) {
                allKeywords.add(keyword);
            }
        }
        setKeywords(allKeywords);
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }

    public List<Speaker> getSpeakers() {
        return Collections.unmodifiableList(speakers);
    }

    public void setSpeakers(final List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public void addSpeaker(final Speaker speaker) {
        if (speaker != null && !speakers.contains(speaker)) {
            ArrayList<Speaker> newSpeakers = new ArrayList<Speaker>(speakers);
            newSpeakers.add(speaker);
            setSpeakers(newSpeakers);
        }
    }

    public void removeSpeaker(final Speaker speaker) {
        if (speaker != null && speakers.contains(speaker)) {
            ArrayList<Speaker> newSpeakers = new ArrayList<Speaker>(speakers);
            newSpeakers.remove(speaker);
            setSpeakers(newSpeakers);
        }
    }

    public String getKeywordsAsString(final String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (String keyword : keywords) {
            if (builder.length() > 0) {
                builder.append(delimiter);
            }
            builder.append(keyword);
        }
        return builder.toString();
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(final boolean published) {        
        this.published = published;
    }

    public String getExpectedAudience() {
        return expectedAudience;
    }

    public void setExpectedAudience(final String expectedAudience) {        
        this.expectedAudience = expectedAudience;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(final String outline) {
        this.outline = outline;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(final String equipment) {
        this.equipment = equipment;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }

    /**
     * Compares based on the sessions name.
     */
    public int compareTo(final Session other) {
        return new CompareToBuilder().append(title, other == null ? null : other.getTitle()).toComparison();
    }

    public Iterator<Speaker> iterator() {
        return new ArrayList<Speaker>(speakers).iterator();
    }

    public void sync(Session other) {
        setTimeslot(other.getTimeslot());
        setState(other.getState());
        setFormat(other.getFormat());
        setRoom(other.getRoom());
        setLevel(other.getLevel());
        setTitle(other.getTitle());
        setLead(other.getLead());
        setBody(other.getBody());
        setLanguage(other.getLanguage());
        setKeywords(other.getKeywords());
        setSpeakers(other.getSpeakers());
        setPublished(other.isPublished());
        setExpectedAudience(other.getExpectedAudience());
        setOutline(other.getOutline());
        setEquipment(other.getEquipment());
        setFeedback(other.getFeedback());        
    }
}
