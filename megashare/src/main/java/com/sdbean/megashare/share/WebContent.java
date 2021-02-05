package com.sdbean.megashare.share;

import android.graphics.Bitmap;

final public class WebContent
{

    private final String webpageUrl;
    private final String title;
    private final String quote;
    private final String tag;
    private final Bitmap thumbImage;

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getQuote() {
        return quote;
    }

    public String getTag() {
        return tag;
    }

    public Bitmap getThumbImage() {
        return thumbImage;
    }

    public static class Builder {
        private String webpageUrl = null;
        private String title      = null;
        private String quote      = null;
        private String tag        = null;
        private Bitmap thumbImage = null;

        public Builder(String webpageUrl) {
            this.webpageUrl = webpageUrl;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder quote(String val) {
            quote = val;
            return this;
        }
        public Builder hashTag(String val) {
            tag = val;
            return this;
        }
        public Builder thumbImage(Bitmap val) {
            thumbImage = val;
            return this;
        }

        public WebContent build() {
            return new WebContent(this);
        }
    }

    private WebContent(Builder builder) {
        webpageUrl = builder.webpageUrl;
        title = builder.title;
        quote = builder.quote;
        tag = builder.tag;
        thumbImage = builder.thumbImage;
    }
}
