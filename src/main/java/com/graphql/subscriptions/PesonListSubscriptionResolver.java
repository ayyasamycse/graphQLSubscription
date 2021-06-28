package com.graphql.subscriptions;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graphql.entity.Person;
import com.graphql.header.HeaderRepository;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;

@Component
public class PesonListSubscriptionResolver implements GraphQLSubscriptionResolver{
	private final HeaderRepository repository;
	@Autowired
	public PesonListSubscriptionResolver(final HeaderRepository repository) {
        this.repository = repository ;
	}
	public Publisher<List<Person>> getPersons(){
		return s->
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
			List<Person> persons = repository.findAll();
			s.onNext(persons);
			s.onComplete();
		},0,2,TimeUnit.SECONDS);
	}
}
