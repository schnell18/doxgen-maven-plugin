package cf.tinkerit.dingo.api.service;

import cf.tinkerit.dingo.api.model.*;
import cf.tinkerit.dingo.api.query.PageQuery;
import cf.tinkerit.dingo.api.query.ServeQuery;
import cf.tinkerit.dingo.api.query.ServeSetQuery;
import cf.tinkerit.dingo.api.result.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务及套餐查询
 */
public interface ServeInquiryService {

    /**
     * 根据serveId,获取Serve对象
     * <p>此接口会缓存结果入Tair，缓存过期时间为10小时
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param serveId 服务ID
     * @return 服务对象
     */
    CallResult<Serve> queryServeById(long serveId);

    /**
     * 根据服务类型, 获取Serve集合
     * <p>此接口会缓存结果入Tair，缓存过期时间为10小时
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param serviceType 服务类型
     * @return 服务查询结果对象
     */
    CallResult<ArrayList<Serve>> queryServesByType(int serviceType);

    /**
     * 根据服务ID列表, 批量获取服务
     * <p>此接口会缓存结果入Tair，缓存过期时间为10小时
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param serveIds 服务ID列表
     * @return 服务批量查询结果对象 serveIds中id不存在用NULL填充并返回list中其他存在的id结果
     */
    CallResult<ArrayList<Serve>> bulkQueryServesById(List<Long> serveIds);

    /**
     * 服务列表分页查询
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param req 检索条件
     * @return 服务查询结果对象
     */
    CallResult<PageResult<Serve>> pageQueryServes(PageQuery<ServeQuery> req);

    /**
     * 根据服务ID和供应商找到服务的结算信息
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param serveId 服务ID
     * @param vendorId 如供应商为空, 则返回首选供应商的结算信息
     * @return 服务聚合查询结果对象 如果查不到相应的serve，serveTax, serveVendorConfig，则返回空
     */
    CallResult<ServeAggregation> queryServeAggregation(long serveId, long vendorId);

    /**
     * 服务列表查询
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#SYSTEM_ERROR}: 系统错误</li>
     * </ul>
     * @param req 检索条件
     * @return 服务查询结果对象
     */
    CallResult<ArrayList<Serve>> queryServes(ServeQuery req);

    /**
     * 单个查询服务套餐
     * <p>如果是复合套餐类型，则填充ServeSetAggregation的childServeSetList即为该复合套餐关联的普通套餐,serveList留空;
     * 如果是普通套餐类型，则填充ServeSetAggregation的serveList即为该普通套餐关联的服务,childServeSetList留空.
     * <p>此接口会缓存结果入Tair，缓存过期时间为10小时
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}: 参数错误</li>
     * </ul>
     * @param serveSetId 服务套餐ID
     * @return 服务套餐查询结果
     */
    CallResult<ServeSetAggregation> queryServeSetById(long serveSetId);

    /**
     * 根据套餐ids批量获取
     * <p>如果是复合套餐类型，则填充ServeSetAggregation的childServeSetList即为该复合套餐关联的普通套餐,serveList留空;
     * 如果是普通套餐类型，则填充ServeSetAggregation的serveList即为该普通套餐关联的服务,childServeSetList留空.
     * <p>此接口会缓存结果入Tair，缓存过期时间为10小时
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}</li>
     * </ul>
     * @param serveSetIdList 服务套餐ID列表
     * @return 服务批量查询结果，serveSetIdList中id不存在用NULL填充并返回list中其他存在的id结果
     *
     */
    CallResult<ArrayList<ServeSetAggregation>> bulkQueryServeSetsById(List<Long> serveSetIdList);

    /**
     * 服务套餐列表
     * <p>如果是复合套餐类型，则填充ServeSetAggregation的childServeSetList即为该复合套餐关联的普通套餐,serveList留空;
     * 如果是普通套餐类型，则填充ServeSetAggregation的serveList即为该普通套餐关联的服务,childServeSetList留空.
     * <p>此方法可能返回以下错误码:<br>
     * <ul>
     *     <li>{@link cf.tinkerit.dingo.api.result.ErrorCode#PARAM_ERROR}</li>
     * </ul>
     * @param req 服务套餐检索条件
     * @return 服务套餐分页查询结果，如果单个套餐ID不存在返回NULL
     */
    CallResult<PageResult<ServeSetAggregation>> pageQueryServeSet(PageQuery<ServeSetQuery> req);

}
