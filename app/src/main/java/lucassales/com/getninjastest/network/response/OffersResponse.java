package lucassales.com.getninjastest.network.response;

import java.util.List;
import java.util.Map;

import lucassales.com.getninjastest.network.dto.HttpReferenceDto;
import lucassales.com.getninjastest.network.dto.OfferDto;

/**
 * Created by lucas on 30/01/17.
 */

public class OffersResponse {
    private List<OfferDto> offers;
    private Map<String, HttpReferenceDto> _links;

    public List<OfferDto> getOffers() {
        return offers;
    }

    public Map<String, HttpReferenceDto> getLinks() {
        return _links;
    }
}
