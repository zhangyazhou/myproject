package com.mycontainer.ex06.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

public class SimplePipeline implements Pipeline, Lifecycle {

	// The basic Valve (if any) associated with this Pipeline.
	protected Valve basic = null;
	// The Container with which this Pipeline is associated.
	protected Container container = null;
	// the array of Valves
	protected Valve valves[] = new Valve[0];

	public SimplePipeline(Container container) {
		setContainer(container);
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public Valve getBasic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBasic(Valve valve) {
		this.basic = valve;
		((Contained) valve).setContainer(container);
	}

	@Override
	public void addValve(Valve valve) {
		if (valve instanceof Contained)
			((Contained) valve).setContainer(this.container);

		synchronized (valves) {
			Valve results[] = new Valve[valves.length + 1];
			System.arraycopy(valves, 0, results, 0, valves.length);
			results[valves.length] = valve;
			valves = results;
		}
	}

	@Override
	public Valve[] getValves() {
		return valves;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// Invoke the first Valve in this pipeline for this request
		(new StandardPipelineValveContext()).invokeNext(request, response);
	}

	@Override
	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub

	}
	
	protected class StandardPipelineValveContext implements ValveContext {
		protected int stage = 0;
		@Override
		public String getInfo() {
			return null;
		}

		@Override
		public void invokeNext(Request request, Response response) throws IOException, ServletException {
			int subscript = stage;
			stage = stage + 1;
			if (subscript < valves.length) {
				valves[subscript].invoke(request, response, this);
			}
			else if ((subscript == valves.length) && (basic != null)) {
				basic.invoke(request, response, this);
			}
			else {
				throw new ServletException("No valve");
			}
		}
	}

}
