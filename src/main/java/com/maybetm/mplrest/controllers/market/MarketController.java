package com.maybetm.mplrest.controllers.market;

import com.maybetm.mplrest.market.Market;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:52
 */

@RestController
@RequestMapping("market")
public class MarketController {

	public List<Market> getMarkets(@PathVariable("id") Market market) {
		return null;
	}
}
