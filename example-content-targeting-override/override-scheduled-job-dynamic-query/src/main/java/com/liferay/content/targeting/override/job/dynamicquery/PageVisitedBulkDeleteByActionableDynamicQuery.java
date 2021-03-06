/**
 * © 2018 Liferay, Inc. <https://liferay.com>
 *
 * SPDX-License-Identifier: LGPL-2.1-or-later
 */

package com.liferay.content.targeting.override.job.dynamicquery;

import com.liferay.content.targeting.analytics.service.AnalyticsEventLocalService;
import com.liferay.content.targeting.override.job.ScheduledBulkOperation;
import com.liferay.content.targeting.rule.visited.model.PageVisited;
import com.liferay.content.targeting.rule.visited.service.PageVisitedLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 */
@Component(
	property = {
		"model.class=com.liferay.content.targeting.rule.visited.model.PageVisited",
		"service.ranking:Integer=50"
	},
	service = ScheduledBulkOperation.class
)
public class PageVisitedBulkDeleteByActionableDynamicQuery
	extends BulkDeleteByActionableDynamicQuery<PageVisited> {

	@Override
	protected PageVisited delete(PageVisited pageVisited) {
		return pageVisitedLocalService.deletePageVisited(pageVisited);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return pageVisitedLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String getDateColumnName() {
		return "modifiedDate";
	}

	@Override
	protected Date getMaxDate() throws PortalException {
		return analyticsEventLocalService.getMaxAge();
	}

	@Override
	protected String getTableName() {
		return "CT_Visited_ContentVisited";
	}

	@Override
	@Reference
	protected void setPortal(Portal portal) {
		super.setPortal(portal);
	}

	@Reference
	protected AnalyticsEventLocalService analyticsEventLocalService;

	@Reference
	protected PageVisitedLocalService pageVisitedLocalService;

}