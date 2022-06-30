package com.ansi.busticketsalesautomation;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BusTicket.db";

    /* User Table */
    public static final String USER_TABLE = "user_table";
    public static final String USER_TABLE_COL_1 = "Id";
    public static final String USER_TABLE_COL_2 = "FirstName";
    public static final String USER_TABLE_COL_3 = "LastName";
    public static final String USER_TABLE_COL_4 = "Email";
    public static final String USER_TABLE_COL_5 = "PhoneNumber";
    public static final String USER_TABLE_COL_6 = "Gender";
    public static final String USER_TABLE_COL_7 = "Password";

    /* Company Table */
    public static final String COMPANY_TABLE = "company_table";
    public static final String COMPANY_TABLE_COL_1 = "Id";
    public static final String COMPANY_TABLE_COL_2 = "CompanyName";
    public static final String COMPANY_TABLE_COL_3 = "Username";
    public static final String COMPANY_TABLE_COL_4 = "Password";
    public static final String COMPANY_TABLE_COL_5 = "LogoPath";

    /* Expedition Table */
    public static final String EXPEDITION_TABLE = "expedition_table";
    public static final String EXPEDITION_TABLE_COL_1 = "Id";
    public static final String EXPEDITION_TABLE_COL_2 = "CompanyId";
    public static final String EXPEDITION_TABLE_COL_3 = "CityFrom";
    public static final String EXPEDITION_TABLE_COL_4 = "CityTo";
    public static final String EXPEDITION_TABLE_COL_5 = "DepartureTime";
    public static final String EXPEDITION_TABLE_COL_6 = "Price";

    /* Ticket Table */
    public static final String TICKET_TABLE = "ticket_table";
    public static final String TICKET_TABLE_COL_1 = "Id";
    public static final String TICKET_TABLE_COL_2 = "UserId";
    public static final String TICKET_TABLE_COL_3 = "ExpeditionId";
    public static final String TICKET_TABLE_COL_4 = "DepartureDate";
    public static final String TICKET_TABLE_COL_5 = "Gender";
    public static final String TICKET_TABLE_COL_6 = "SeatNumber";
    public static final String TICKET_TABLE_COL_7 = "Price";

    /* Campaign Table */
    public static final String CAMPAIGN_TABLE = "campaign_table";
    public static final String CAMPAIGN_TABLE_COL_1 = "Id";
    public static final String CAMPAIGN_TABLE_COL_2 = "Title";
    public static final String CAMPAIGN_TABLE_COL_3 = "Content";
    public static final String CAMPAIGN_TABLE_COL_4 = "StartingDate";
    public static final String CAMPAIGN_TABLE_COL_5 = "Deadline";
    public static final String CAMPAIGN_TABLE_COL_6 = "State";

    /* Ticket Reservation Table */
    public static final String TICKET_RESERVATION_TABLE = "ticket_reservation_table";
    public static final String TICKET_RESERVATION_TABLE_COL_1 = "Id";
    public static final String TICKET_RESERVATION_TABLE_COL_2 = "ExpeditionId";
    public static final String TICKET_RESERVATION_TABLE_COL_3 = "FirstName";
    public static final String TICKET_RESERVATION_TABLE_COL_4 = "LastName";
    public static final String TICKET_RESERVATION_TABLE_COL_5 = "PhoneNumber";
    public static final String TICKET_RESERVATION_TABLE_COL_6 = "Email";
    public static final String TICKET_RESERVATION_TABLE_COL_7 = "DepartureDate";
    public static final String TICKET_RESERVATION_TABLE_COL_8 = "Gender";
    public static final String TICKET_RESERVATION_TABLE_COL_9 = "SeatNumber";
    public static final String TICKET_RESERVATION_TABLE_COL_10 = "Price";

    /* Admin Table */
    public static final String ADMIN_TABLE = "admin_table";
    public static final String ADMIN_TABLE_COL_1 = "Id";
    public static final String ADMIN_TABLE_COL_2 = "FirstName";
    public static final String ADMIN_TABLE_COL_3 = "LastName";
    public static final String ADMIN_TABLE_COL_4 = "Username";
    public static final String ADMIN_TABLE_COL_5 = "Password";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Create User Table */
        db.execSQL("create table " + USER_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Email TEXT, PhoneNumber TEXT, Gender TEXT, Password TEXT)");

        /* Create Company Table */
        db.execSQL("create table " + COMPANY_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, CompanyName TEXT, Username TEXT, Password TEXT, LogoPath TEXT)");

        /* Create Expedition Table */
        db.execSQL("create table " + EXPEDITION_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, CompanyId INTEGER, CityFrom TEXT, CityTo TEXT, DepartureTime TEXT, Price INTEGER)");

        /* Create Ticket Table */
        db.execSQL("create table " + TICKET_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, UserId INTEGER, ExpeditionId INTEGER, DepartureDate TEXT, Gender TEXT, SeatNumber INTEGER, Price INTEGER)");

        /* Create Campaign Table */
        db.execSQL("create table " + CAMPAIGN_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Content TEXT, StartingDate TEXT, Deadline TEXT, State INTEGER)");

        /* Create Ticket Reservation Table */
        db.execSQL("create table " + TICKET_RESERVATION_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, ExpeditionId INTEGER, FirstName TEXT, LastName TEXT, PhoneNumber TEXT, Email TEXT, DepartureDate TEXT, Gender TEXT, SeatNumber INTEGER, Price INTEGER)");

        /* Create Admin Table */
        db.execSQL("create table " + ADMIN_TABLE + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Username TEXT, Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ COMPANY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ EXPEDITION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ TICKET_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ CAMPAIGN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ TICKET_RESERVATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ ADMIN_TABLE);
        onCreate(db);

    }

    public  boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COL_2, user.getFirstName());
        contentValues.put(USER_TABLE_COL_3, user.getLastName());
        contentValues.put(USER_TABLE_COL_4, user.getEmail());
        contentValues.put(USER_TABLE_COL_5, user.getPhoneNumber());
        contentValues.put(USER_TABLE_COL_6, user.getGender());
        contentValues.put(USER_TABLE_COL_7, user.getPassword());
        long result = db.insert(USER_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public boolean updateUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COL_2, user.getFirstName());
        contentValues.put(USER_TABLE_COL_3, user.getLastName());
        contentValues.put(USER_TABLE_COL_4, user.getEmail());
        contentValues.put(USER_TABLE_COL_5, user.getPhoneNumber());
        contentValues.put(USER_TABLE_COL_6, user.getGender());
        contentValues.put(USER_TABLE_COL_7, user.getPassword());

        long result = db.update(USER_TABLE, contentValues, "Id = ?", new String[]{String.valueOf(user.getId())});

        if (result == -1){
            return false;
        }
        return true;
    }



    public void deleteUser(int userId){
        deleteTicketsByUserId(userId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, "Id=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public List<User> getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + USER_TABLE, null);

        List<User> users = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            users.add(new User(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6)));
        }
        return users;
    }

    public User getUserById(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + USER_TABLE + " where Id = " + userId, null);

        User user = null;
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            user = new User(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6));
        }
        return user;
    }

    public User getUserByPhoneNumber(String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + USER_TABLE + " where PhoneNumber = '"+phoneNumber+"'", null);
        User user = null;
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            user = new User(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6));
        }
        return user;
    }

    public User getUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + USER_TABLE + " where Email = '"+email+"'", null);
        User user = null;
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            user = new User(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6));
        }
        return user;
    }

    public  boolean insertCompany(Company company){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_TABLE_COL_2, company.getCompanyName());
        contentValues.put(COMPANY_TABLE_COL_3, company.getUsername());
        contentValues.put(COMPANY_TABLE_COL_4, company.getPassword());
        contentValues.put(COMPANY_TABLE_COL_5, company.getLogoPath());

        long result = db.insert(COMPANY_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public boolean updateCompany(Company company){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_TABLE_COL_2, company.getCompanyName());
        contentValues.put(COMPANY_TABLE_COL_3, company.getUsername());
        contentValues.put(COMPANY_TABLE_COL_4, company.getPassword());
        contentValues.put(COMPANY_TABLE_COL_5, company.getLogoPath());

        long result = db.update(COMPANY_TABLE, contentValues, "Id = ?", new String[]{String.valueOf(company.getId())});

        if (result == -1){
            return false;
        }
        return true;
    }

    public void deleteCompany(int companyId){
        deleteTicketsReservationByCompanyId(companyId);
        deleteTicketsByCompanyId(companyId);
        deleteExpeditionsByCompanyId(companyId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMPANY_TABLE, "Id=?", new String[]{String.valueOf(companyId)});
        db.close();
    }

    private void deleteTicketsByCompanyId(int companyId){

        List<Ticket> tickets = getTicketsByCompanyId(companyId);

        for (Ticket ticket : tickets) {
            deleteTicket(ticket.getId());
        }
    }

    private void deleteTicketsReservationByCompanyId(int companyId){

        List<TicketReservationDto> tickets = getTicketReservationByCompanyId(companyId);

        for (TicketReservationDto ticket : tickets) {
            deleteTicketReservation(ticket.getTicketReservation().getId());
        }
    }

    public List<Company> getCompanies(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + COMPANY_TABLE, null);

        List<Company> companies = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            companies.add(new Company(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }
        return companies;
    }

    public List<CompanyDto> getCompaniesAndExpeditions(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + COMPANY_TABLE, null);

        List<CompanyDto> companies = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            Company company = new Company(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4));
            List<Expedition> expeditions = getExpeditionsByCompanyId(company.getId());
            companies.add(new CompanyDto(company, expeditions));
        }
        return companies;
    }

    public List<CompanyDto> getCompanyAndExpeditionsByCompanyName(String companyName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + COMPANY_TABLE + " where CompanyName like '%"+companyName+"%'", null);

        List<CompanyDto> companies = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            Company company = new Company(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4));
            List<Expedition> expeditions = getExpeditionsByCompanyId(company.getId());
            companies.add(new CompanyDto(company, expeditions));
        }
        return companies;
    }

    public Company getCompanyById(int companyId){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + COMPANY_TABLE + " where Id = " + companyId, null);

        Company company = null;
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            company = new Company(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4));
        }
        return company;
    }

    public  boolean insertExpedition(Expedition expedition){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPEDITION_TABLE_COL_2, expedition.getCompanyId());
        contentValues.put(EXPEDITION_TABLE_COL_3, expedition.getCityFrom());
        contentValues.put(EXPEDITION_TABLE_COL_4, expedition.getCityTo());
        contentValues.put(EXPEDITION_TABLE_COL_5, expedition.getDepartureTime());
        contentValues.put(EXPEDITION_TABLE_COL_6, expedition.getPrice());

        long result = db.insert(EXPEDITION_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public List<Expedition> getExpeditionsByCompanyId(int companyId){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + EXPEDITION_TABLE + " where CompanyId = " + companyId, null);

        List<Expedition> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            expeditions.add(new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5))));
        }
        return expeditions;
    }

    public void deleteExpedition(int expeditionId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPEDITION_TABLE, "Id=?", new String[]{String.valueOf(expeditionId)});
        db.close();
    }

    public void deleteExpeditionsByCompanyId(int companyId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPEDITION_TABLE, "CompanyId=?", new String[]{String.valueOf(companyId)});
        db.close();
    }

    public boolean updateExpedition(Expedition expedition){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPEDITION_TABLE_COL_2, expedition.getCompanyId());
        contentValues.put(EXPEDITION_TABLE_COL_3, expedition.getCityFrom());
        contentValues.put(EXPEDITION_TABLE_COL_4, expedition.getCityTo());
        contentValues.put(EXPEDITION_TABLE_COL_5, expedition.getDepartureTime());
        contentValues.put(EXPEDITION_TABLE_COL_6, expedition.getPrice());

        long result = db.update(EXPEDITION_TABLE, contentValues, "Id = ?", new String[]{String.valueOf(expedition.getId())});

        if (result == -1){
            return false;
        }
        return true;
    }

    public List<Expedition> getExpeditions(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + EXPEDITION_TABLE, null);

        List<Expedition> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            expeditions.add(new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5))));
        }
        return expeditions;
    }

    public ExpeditionDto getExpeditionById(int expeditionId){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from expedition_table where Id = " + expeditionId;
        Cursor result = db.rawQuery(query, null);

        ExpeditionDto expeditionDto = null;
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditionDto = new ExpeditionDto(expedition, company);
        }
        return expeditionDto;
    }

    public List<ExpeditionDto> getExpeditionsByCityFromAndCityTo(String cityFrom, String cityTo){

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat formatterDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatterTime.format(date).toString();
        String nowDate = formatterDay.format(date).toString();

        String query = null;

        if (nowDate.equals(TravelInformation.DEPARTURE_DATE)){
            query = "select *from expedition_table where CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"' and DepartureTime > '"+time+"' and DepartureTime > '"+time+"'" + " order by DepartureTime";
        }
        else{
            query = "select *from expedition_table where CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"'" + " order by DepartureTime";
        }
        Cursor result = db.rawQuery(query, null);

        List<ExpeditionDto> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditions.add(new ExpeditionDto(expedition, company));
        }
        return expeditions;
    }

    public List<ExpeditionDto> getExpeditionsByCompanyIdAndCityFromAndCityToForTicketReservation(int companyId, String cityFrom, String cityTo){

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat formatterDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatterTime.format(date).toString();
        String nowDate = formatterDay.format(date).toString();

        String query = null;

        if (nowDate.equals(TravelInformation.DEPARTURE_DATE)){
            query = "select *from expedition_table where CompanyId = " + companyId + " and CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"' and DepartureTime > '"+time+"' and DepartureTime > '"+time+"'" + " order by DepartureTime";
        }
        else{
            query = "select *from expedition_table where CompanyId = " + companyId + " and CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"'" + " order by DepartureTime";
        }
        Cursor result = db.rawQuery(query, null);

        List<ExpeditionDto> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditions.add(new ExpeditionDto(expedition, company));
        }
        return expeditions;
    }

    public List<ExpeditionDto> getExpeditionsByCityFromAndCityToForTicketReservation(String cityFrom, String cityTo){

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat formatterDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatterTime.format(date).toString();
        String nowDate = formatterDay.format(date).toString();

        String query = null;

        if (nowDate.equals(TravelInformation.DEPARTURE_DATE)){
            query = "select *from expedition_table where CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"' and DepartureTime > '"+time+"' and DepartureTime > '"+time+"'" + " order by DepartureTime";
        }
        else{
            query = "select *from expedition_table where CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"'" + " order by DepartureTime";
        }
        Cursor result = db.rawQuery(query, null);

        List<ExpeditionDto> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditions.add(new ExpeditionDto(expedition, company));
        }
        return expeditions;
    }

    public List<ExpeditionDto> getExpeditionsByCompanyIdAndCityFromAndCityTo(int companyId, String cityFrom, String cityTo){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from expedition_table where CompanyId = " + companyId + " and CityFrom = '"+cityFrom+"' and CityTo = '"+cityTo+"'" + " order by DepartureTime";
        Cursor result = db.rawQuery(query, null);

        List<ExpeditionDto> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditions.add(new ExpeditionDto(expedition, company));
        }
        return expeditions;
    }

    public List<ExpeditionDto> getExpeditionsDtoByCompanyId(int companyId){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from expedition_table where CompanyId = " + companyId + " order by DepartureTime";
        Cursor result = db.rawQuery(query, null);

        List<ExpeditionDto> expeditions = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Expedition expedition = new Expedition(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)));
            Company company = getCompanyById(expedition.getCompanyId());
            expeditions.add(new ExpeditionDto(expedition, company));
        }
        return expeditions;
    }

    public  boolean insertTicket(Ticket ticket){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TICKET_TABLE_COL_2, ticket.getUserId());
        contentValues.put(TICKET_TABLE_COL_3, ticket.getExpeditionId());
        contentValues.put(TICKET_TABLE_COL_4, ticket.getDepartureDate());
        contentValues.put(TICKET_TABLE_COL_5, ticket.getGender());
        contentValues.put(TICKET_TABLE_COL_6, ticket.getSeatNumber());
        contentValues.put(TICKET_TABLE_COL_7, ticket.getPrice());

        long result = db.insert(TICKET_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public void deleteTicket(int ticketId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TICKET_TABLE, "Id=?", new String[]{String.valueOf(ticketId)});
        db.close();
    }

    private void deleteTicketsByUserId(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TICKET_TABLE, "UserId=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void deleteTicketsByExpeditionId(int expeditionId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TICKET_TABLE, "ExpeditionId=?", new String[]{String.valueOf(expeditionId)});
        db.close();
    }

    List<TicketDto> oldTickets = null;

    public List<TicketDto> getFutureTicketByUserId(int userId){

        SQLiteDatabase db = this.getWritableDatabase();

        oldTickets = new ArrayList<>();

        SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatterTime.format(date).toString();
        String nowDate = formatterDay.format(date).toString();
        String nowDateFormat = formatterDate.format(date).toString();

        String query = "select Id, UserId, ExpeditionId, DepartureDate, Gender, SeatNumber, Price, date(substr(DepartureDate, 7, 4) || '-' || substr(DepartureDate, 4, 2) || '-' || substr(DepartureDate, 1, 2)) as departureDateFormat from ticket_table where UserId = " + userId + " and departureDateFormat >= '" + nowDate + "'" + " order by DepartureDate";
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());

            String[] departureHourAndMinute = expeditionDto.getExpedition().getDepartureTime().split(":");
            String[] nowHourAndMinute = time.split(":");

            if (nowDateFormat.equals(ticket.getDepartureDate())){
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                try {
                    Date departureTime = parser.parse(expeditionDto.getExpedition().getDepartureTime());
                    Date nowTime = parser.parse(time);

                    if (departureTime.after(nowTime)){
                        tickets.add(new TicketDto(ticket, expeditionDto));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
                tickets.add(new TicketDto(ticket, expeditionDto));
            }
        }
        return tickets;
    }

    public List<TicketDto> getOldTicketByUserId(int userId){

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatterTime.format(date).toString();
        String nowDate = formatterDay.format(date).toString();
        String nowDateFormat = formatterDate.format(date).toString();


        String query = "select Id, UserId, ExpeditionId, DepartureDate, Gender, SeatNumber, Price, date(substr(DepartureDate, 7, 4) || '-' || substr(DepartureDate, 4, 2) || '-' || substr(DepartureDate, 1, 2)) as departureDateFormat from ticket_table where UserId = " + userId + " and departureDateFormat <= '" + nowDate + "'" + " order by DepartureDate";
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> tickets = new ArrayList<>();

        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());

            String[] departureHourAndMinute = expeditionDto.getExpedition().getDepartureTime().split(":");
            String[] nowHourAndMinute = time.split(":");

            if (nowDateFormat.equals(ticket.getDepartureDate())){
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                try {
                    Date departureTime = parser.parse(expeditionDto.getExpedition().getDepartureTime());
                    Date nowTime = parser.parse(time);

                    if (departureTime.before(nowTime)){
                        tickets.add(new TicketDto(ticket, expeditionDto));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
                tickets.add(new TicketDto(ticket, expeditionDto));
            }
        }
        return tickets;
    }

    public List<Ticket> getTicketsByCompanyId(int companyId){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from ticket_table";
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());
            tickets.add(new TicketDto(ticket, expeditionDto));
        }

        List<Ticket> tickets2 = new ArrayList<>();
        for (TicketDto ticket : tickets) {
            if (ticket.getExpeditionDto().getCompany().getId() == companyId){
                tickets2.add(ticket.getTicket());
            }
        }
        return tickets2;
    }

    public List<TicketDto> getTicketByUserIdAndDepartureDate(int userId, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from ticket_table where UserId = " + userId + " and DepartureDate = '"+ departureDate +"'";
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());
            tickets.add(new TicketDto(ticket, expeditionDto));
        }
        return tickets;
    }

    public List<TicketDto> getTicketsByExpeditionIdAndDepartureDate(int expeditionId, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from ticket_table where DepartureDate = '"+departureDate+"' and ExpeditionId = " + expeditionId;
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());
            tickets.add(new TicketDto(ticket, expeditionDto));
        }
        return tickets;
    }

    public List<TicketDto> getTicketsByUserIdAndCompanyIdAndCityFromAndCityToAndDepartureDate(int userId, int companyId, String cityFrom, String cityTo, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from ticket_table where UserId = " + userId + " and DepartureDate = '"+ departureDate +"'";
        Cursor result = db.rawQuery(query, null);

        List<TicketDto> ticketDto = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());
            ticketDto.add(new TicketDto(ticket, expeditionDto));
        }

        List<TicketDto> tickets = new ArrayList<>();
        for (TicketDto ticket : ticketDto) {
            if (ticket.getExpeditionDto().getExpedition().getCityFrom().equals(cityFrom) && ticket.getExpeditionDto().getExpedition().getCityTo().equals(cityTo) && ticket.getExpeditionDto().getCompany().getId() == companyId){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public List<TicketDtoAndUserDto> getTicketsByCompanyIdAndCityFromAndCityToAndDepartureDateAndDepartureHour(int companyId, String cityFrom, String cityTo, String departureDate, String departureHour){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from ticket_table where DepartureDate = '"+ departureDate +"'";
        Cursor result = db.rawQuery(query, null);


        List<TicketDto> ticketDto = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            ExpeditionDto expeditionDto = getExpeditionById(ticket.getExpeditionId());
            ticketDto.add(new TicketDto(ticket, expeditionDto));
        }

        List<TicketDtoAndUserDto> tickets = new ArrayList<>();
        for (TicketDto ticket : ticketDto) {
            if (ticket.getExpeditionDto().getExpedition().getCityFrom().equals(cityFrom) && ticket.getExpeditionDto().getExpedition().getCityTo().equals(cityTo) && ticket.getExpeditionDto().getExpedition().getDepartureTime().equals(departureHour) && ticket.getExpeditionDto().getCompany().getId() == companyId){
                User user = getUserById(ticket.getTicket().getUserId());
                tickets.add(new TicketDtoAndUserDto(ticket, user));
            }
        }
        return tickets;
    }

    public TicketDtoExtend getTicketsByPhoneNumberAndCompanyIdAndCityFromAndCityToAndDepartureDate(int companyId, String phoneNumber, String cityFrom, String cityTo, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();
        User user = getUserByPhoneNumber(phoneNumber);
        if (user != null){
            List<TicketDto> ticketDto = getTicketsByUserIdAndCompanyIdAndCityFromAndCityToAndDepartureDate(user.getId(), companyId, cityFrom, cityTo, departureDate);
            TicketDtoExtend ticketDtoExtend = new TicketDtoExtend(ticketDto, user);
            return ticketDtoExtend;
        }
        else{
            List<TicketDto> ticketDto = getTicketsByUserIdAndCompanyIdAndCityFromAndCityToAndDepartureDate(0, companyId, cityFrom, cityTo, departureDate);
            TicketDtoExtend ticketDtoExtend = new TicketDtoExtend(ticketDto, new User());
            return ticketDtoExtend;
        }
    }

    public List<TicketAndUserDto> getPassengersByExpeditionIdAndDate(int expeditionId, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select *from ticket_table where ExpeditionId = " + expeditionId + " and DepartureDate = '"+departureDate+"'" + " order by SeatNumber";
        Cursor result = db.rawQuery(query, null);

        List<TicketAndUserDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            User user = getUserById(ticket.getUserId());
            tickets.add(new TicketAndUserDto(ticket, user));
        }
        return tickets;
    }

    public List<Ticket> getTickets(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + TICKET_TABLE, null);

        List<Ticket> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            tickets.add(new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6))));
        }
        return tickets;
    }

    public List<TicketDtoAndUserDto> getTicketsDto(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + TICKET_TABLE, null);

        List<TicketDtoAndUserDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            TicketDto ticketDto = new TicketDto(ticket, getExpeditionById(ticket.getExpeditionId()));
            User user = getUserById(ticket.getUserId());
            tickets.add(new TicketDtoAndUserDto(ticketDto, user));
        }
        return tickets;
    }

    public List<TicketDtoAndUserDto> getTicketsDtoByPhoneNumber(String phoneNumber){

        User user = getUserByPhoneNumber(phoneNumber);

        if (user != null){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor result = db.rawQuery("select *from " + TICKET_TABLE + " where UserId = " + user.getId(), null);

            List<TicketDtoAndUserDto> tickets = new ArrayList<>();
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()){
                Ticket ticket = new Ticket(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
                TicketDto ticketDto = new TicketDto(ticket, getExpeditionById(ticket.getExpeditionId()));
                tickets.add(new TicketDtoAndUserDto(ticketDto, user));
            }
            return tickets;
        }
        return null;
    }

    public  boolean insertCampaign(Campaign campaign){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAMPAIGN_TABLE_COL_2, campaign.getTitle());
        contentValues.put(CAMPAIGN_TABLE_COL_3, campaign.getContent());
        contentValues.put(CAMPAIGN_TABLE_COL_4, campaign.getStartingDate());
        contentValues.put(CAMPAIGN_TABLE_COL_5, campaign.getDeadline());
        contentValues.put(CAMPAIGN_TABLE_COL_6, campaign.getState());

        long result = db.insert(CAMPAIGN_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public boolean updateCampaign(Campaign campaign){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAMPAIGN_TABLE_COL_2, campaign.getTitle());
        contentValues.put(CAMPAIGN_TABLE_COL_3, campaign.getContent());
        contentValues.put(CAMPAIGN_TABLE_COL_4, campaign.getStartingDate());
        contentValues.put(CAMPAIGN_TABLE_COL_5, campaign.getDeadline());
        contentValues.put(CAMPAIGN_TABLE_COL_6, campaign.getState());

        long result = db.update(CAMPAIGN_TABLE, contentValues, "Id = ?", new String[]{String.valueOf(campaign.getId())});

        if (result == -1){
            return false;
        }
        return true;
    }

    public void deleteCampaign(int campaignId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CAMPAIGN_TABLE, "Id=?", new String[]{String.valueOf(campaignId)});
        db.close();
    }

    public List<Campaign> getCampaigns(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + CAMPAIGN_TABLE + " where State = " + 1, null);

        List<Campaign> campaigns = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            campaigns.add(new Campaign(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4) ,Integer.parseInt(result.getString(5))));
        }
        return campaigns;
    }

    public  boolean insertTicketReservation(TicketReservation ticketReservation){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TICKET_RESERVATION_TABLE_COL_2, ticketReservation.getExpeditionId());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_3, ticketReservation.getFirstName());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_4, ticketReservation.getLastName());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_5, ticketReservation.getPhoneNumber());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_6, ticketReservation.getEmail());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_7, ticketReservation.getDepartureDate());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_8, ticketReservation.getGender());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_9, ticketReservation.getSeatNumber());
        contentValues.put(TICKET_RESERVATION_TABLE_COL_10, ticketReservation.getPrice());

        long result = db.insert(TICKET_RESERVATION_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public void deleteTicketReservation(int ticketReservationId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TICKET_RESERVATION_TABLE, "Id=?", new String[]{String.valueOf(ticketReservationId)});
        db.close();
    }

    public List<TicketReservationDto> getTicketReservationsDto(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from " + TICKET_RESERVATION_TABLE;
        Cursor result = db.rawQuery(query, null);

        List<TicketReservationDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            TicketReservation ticketReservation = new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9)));
            ExpeditionDto expeditionDto = getExpeditionById(ticketReservation.getExpeditionId());
            tickets.add(new TicketReservationDto(ticketReservation, expeditionDto));
        }
        return tickets;
    }

    public List<TicketReservationDto> getTicketReservationsDtoByPhoneNumber(String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from " + TICKET_RESERVATION_TABLE + " where PhoneNumber = '"+phoneNumber+"'";
        Cursor result = db.rawQuery(query, null);

        List<TicketReservationDto> tickets = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            TicketReservation ticketReservation = new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9)));
            ExpeditionDto expeditionDto = getExpeditionById(ticketReservation.getExpeditionId());
            tickets.add(new TicketReservationDto(ticketReservation, expeditionDto));
        }
        return tickets;
    }

    public List<TicketReservation> getTicketReservationsByExpeditionIdAndDepartureDate(int expeditionId, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from ticket_reservation_table where DepartureDate = '"+departureDate+"' and ExpeditionId = " + expeditionId + " order by SeatNumber";
        Cursor result = db.rawQuery(query, null);

        List<TicketReservation> reservations = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            reservations.add(new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9))));
        }
        return reservations;
    }

    public List<TicketReservationDto> getTicketReservationsByPhoneNumberAndCompanyIdAndCityFromAndCityToAndDepartureDate(int companyId, String phoneNumber, String cityFrom, String cityTo, String departureDate){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from ticket_reservation_table where PhoneNumber = '"+ phoneNumber +"' and DepartureDate = '"+ departureDate +"'";
        Cursor result = db.rawQuery(query, null);

        List<TicketReservationDto> ticketReservationDto = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            TicketReservation ticketReservation = new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9)));
            ExpeditionDto expeditionDto = getExpeditionById(ticketReservation.getExpeditionId());
            ticketReservationDto.add(new TicketReservationDto(ticketReservation, expeditionDto));
        }

        List<TicketReservationDto> tickets = new ArrayList<>();
        for (TicketReservationDto ticket : ticketReservationDto) {
            if (ticket.getExpeditionDto().getExpedition().getCityFrom().equals(cityFrom) && ticket.getExpeditionDto().getExpedition().getCityTo().equals(cityTo) && ticket.getExpeditionDto().getCompany().getId() == companyId){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public List<TicketReservationDto> getTicketReservationsByCompanyIdAndCityFromAndCityToAndDepartureDateAndDepartureHour(int companyId, String cityFrom, String cityTo, String departureDate, String departureHour){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from ticket_reservation_table where DepartureDate = '"+ departureDate +"'";
        Cursor result = db.rawQuery(query, null);

        List<TicketReservationDto> ticketReservationDto = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            TicketReservation ticketReservation = new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9)));
            ExpeditionDto expeditionDto = getExpeditionById(ticketReservation.getExpeditionId());
            ticketReservationDto.add(new TicketReservationDto(ticketReservation, expeditionDto));
        }

        List<TicketReservationDto> tickets = new ArrayList<>();
        for (TicketReservationDto ticket : ticketReservationDto) {
            if (ticket.getExpeditionDto().getExpedition().getCityFrom().equals(cityFrom) && ticket.getExpeditionDto().getExpedition().getCityTo().equals(cityTo) && ticket.getExpeditionDto().getExpedition().getDepartureTime().equals(departureHour) && ticket.getExpeditionDto().getCompany().getId() == companyId){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public List<TicketReservationDto> getTicketReservationByCompanyId(int companyId){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select *from ticket_reservation_table";
        Cursor result = db.rawQuery(query, null);

        List<TicketReservationDto> ticketReservationDto = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            TicketReservation ticketReservation = new TicketReservation(Integer.parseInt(result.getString(0)), Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4) ,result.getString(5), result.getString(6), result.getString(7), Integer.parseInt(result.getString(8)), Integer.parseInt(result.getString(9)));
            ExpeditionDto expeditionDto = getExpeditionById(ticketReservation.getExpeditionId());
            ticketReservationDto.add(new TicketReservationDto(ticketReservation, expeditionDto));
        }

        List<TicketReservationDto> tickets = new ArrayList<>();
        for (TicketReservationDto ticket : ticketReservationDto) {
            if (ticket.getExpeditionDto().getCompany().getId() == companyId){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public  boolean insertAdmin(Admin admin){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_TABLE_COL_2, admin.getFirstName());
        contentValues.put(ADMIN_TABLE_COL_3, admin.getLastName());
        contentValues.put(ADMIN_TABLE_COL_4, admin.getUserName());
        contentValues.put(ADMIN_TABLE_COL_5, admin.getPassword());

        long result = db.insert(ADMIN_TABLE, null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public boolean updateAdmin(Admin admin){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_TABLE_COL_2, admin.getFirstName());
        contentValues.put(ADMIN_TABLE_COL_3, admin.getLastName());
        contentValues.put(ADMIN_TABLE_COL_4, admin.getUserName());
        contentValues.put(ADMIN_TABLE_COL_5, admin.getPassword());

        long result = db.update(ADMIN_TABLE, contentValues, "Id = ?", new String[]{String.valueOf(admin.getId())});

        if (result == -1){
            return false;
        }
        return true;
    }



    public void deleteAdmin(int adminId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ADMIN_TABLE, "Id=?", new String[]{String.valueOf(adminId)});
        db.close();
    }

    public List<Admin> getAdmins(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + ADMIN_TABLE, null);

        List<Admin> admins = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            admins.add(new Admin(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }
        return admins;
    }

    public Admin getAdminById(int adminId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select *from " + ADMIN_TABLE + " where Id = " + adminId, null);

        Admin admin = null;
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()){
            admin = new Admin(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), result.getString(3), result.getString(4));

        }
        return admin;
    }
}
