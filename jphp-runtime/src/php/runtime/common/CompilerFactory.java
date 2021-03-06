package php.runtime.common;

import php.runtime.env.Context;
import php.runtime.env.Environment;

abstract public class CompilerFactory {
    abstract public AbstractCompiler getCompiler(Environment env, Context context);
}
