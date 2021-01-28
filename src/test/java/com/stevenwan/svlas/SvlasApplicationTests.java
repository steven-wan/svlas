package com.stevenwan.svlas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stevenwan.svlas.mapper.StockMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SvlasApplicationTests {

	@Autowired
	private StockMapper stockMapper;

	@Test
	void contextLoads() {
		LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
		List list = stockMapper.selectList(lambdaQueryWrapper);
		list.forEach(System.out::println);
	}

}
