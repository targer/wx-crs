package org.open.crs.dto.lucene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/30.
 */
public class TxtSearchResultsDTO {
    private List<TxtSearchResultDTO> txtSearchResultDTOs = new ArrayList<>();

    public List<TxtSearchResultDTO> getTxtSearchResultDTOs() {
        return txtSearchResultDTOs;
    }

    public void setTxtSearchResultDTOs(List<TxtSearchResultDTO> txtSearchResultDTOs) {
        this.txtSearchResultDTOs = txtSearchResultDTOs;
    }
}
