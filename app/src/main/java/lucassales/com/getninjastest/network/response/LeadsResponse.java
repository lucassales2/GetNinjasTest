package lucassales.com.getninjastest.network.response;

import java.util.List;
import java.util.Map;

import lucassales.com.getninjastest.network.dto.HttpReferenceDto;
import lucassales.com.getninjastest.network.dto.LeadDto;

/**
 * Created by lucas on 30/01/17.
 */

public class LeadsResponse {

    private List<LeadDto> leads;
    private Map<String, HttpReferenceDto> _links;

    public Map<String, HttpReferenceDto> getLinks() {
        return _links;
    }

    public List<LeadDto> getLeads() {
        return leads;
    }
}
