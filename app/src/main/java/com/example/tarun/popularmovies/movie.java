package com.example.tarun.popularmovies;

class movie {

    private String mposterpath;
    private String mDate;
    // --Commented out by Inspection (18/09/18, 2:22 PM):private String mTime;
    private String mOverview;
    private String mRating;
    private String mTitle;
    private String mid;
    private String backdrop;

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    // --Commented out by Inspection START (6/19/18, 9:52 PM):
//    public movie(String id,String posterpath)
//    {
//        this.mid = id;
//        this.mposterpath = posterpath;
//    }
// --Commented out by Inspection STOP (6/19/18, 9:52 PM)
    public movie(){}
// --Commented out by Inspection START (18/09/18, 2:22 PM):
//    public movie(String id,String title,String posterPath,String overview,String rating,String releaseDate)
//    {
//        this.mid = id;
//        this.mTitle = title;
//        this.mDate = releaseDate;
//        this.mposterpath = posterPath;
//        this.mOverview = overview;
//        this.mRating = rating;
//    }
// --Commented out by Inspection STOP (18/09/18, 2:22 PM)

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setId(String id)
    {
        mid = id;
    }
    public void setPoster(String poster)
    {
        this.mposterpath = poster;
    }
    public void setmDate(String Date)
    {

        this.mDate = Date.substring(0,4);
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }
// --Commented out by Inspection START (6/19/18, 9:52 PM):
//    public void setmTime(String time)
//    {
//        this.mTime=time;
//    }
// --Commented out by Inspection STOP (6/19/18, 9:52 PM)

    public String getId()
    {
        return mid;
    }
    public String getmDate()
    {
        return mDate;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmRating() {
        return mRating;
    }

// --Commented out by Inspection START (6/19/18, 9:52 PM):
//    public String getmTime() {
//        return mTime;
//    }
// --Commented out by Inspection STOP (6/19/18, 9:52 PM)

    public String getmTitle() {
        return mTitle;
    }

    // --Commented out by Inspection START (6/19/18, 9:52 PM):
//    public String getId()
//    {
//        return mid;
//    }
// --Commented out by Inspection STOP (6/19/18, 9:52 PM)
    public String getPoster()
    {
        return mposterpath;
    }
}
