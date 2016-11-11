package com.lvj.bookoneday.helper;

/**
 * Created by lvjinku on 15/11/9.
 */
public interface PaginatorDelegate {

    void fetchResutlsWithPage(int page);
    void showLoadMoreView(boolean showStatuTag);
}
