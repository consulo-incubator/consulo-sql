package com.dci.intellij.dbn.language.sql.dialect.oracle;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.ChameleonTokenType;
import com.dci.intellij.dbn.language.common.DBLanguageDialectIdentifier;
import com.dci.intellij.dbn.language.common.DBLanguageSyntaxHighlighter;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.element.ChameleonElementType;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.dci.intellij.dbn.language.psql.dialect.oracle.OraclePLSQLLanguageDialect;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;

public class OracleSQLLanguageDialect extends SqlLikeLanguageVersion<SQLLanguage>
{
	ChameleonElementType plsqlChameleonElementType;

	public OracleSQLLanguageDialect(SQLLanguage sqlLanguage)
	{
		super(DBLanguageDialectIdentifier.ORACLE_SQL, sqlLanguage, "oracle_sql_parser_tokens.xml", "oracle_sql_parser_elements.xml", "oracle_sql_element_tokens.idx", "sql_block");
	}

	@Override
	protected Set<ChameleonTokenType> createChameleonTokenTypes()
	{
		Set<ChameleonTokenType> tokenTypes = new HashSet<ChameleonTokenType>();
		OraclePLSQLLanguageDialect plsql = PSQLLanguage.INSTANCE.findVersionByClass(OraclePLSQLLanguageDialect.class);
		tokenTypes.add(new ChameleonTokenType(this, plsql));
		return tokenTypes;
	}

	@Override
	public ChameleonElementType getChameleonTokenType(DBLanguageDialectIdentifier dialectIdentifier)
	{
		if(dialectIdentifier == DBLanguageDialectIdentifier.ORACLE_PLSQL)
		{
			if(plsqlChameleonElementType == null)
			{
				OraclePLSQLLanguageDialect plsql = PSQLLanguage.INSTANCE.findVersionByClass(OraclePLSQLLanguageDialect.class);
				plsqlChameleonElementType = plsql.getChameleonElementType();
			}
			return plsqlChameleonElementType;
		}
		return super.getChameleonTokenType(dialectIdentifier);
	}

	protected DBLanguageSyntaxHighlighter createSyntaxHighlighter()
	{
		return new OracleSQLSyntaxHighlighter(this);
	}

	@NotNull
	@Override
	public Lexer createLexer(@Nullable Project project)
	{
		return new FlexAdapter(new OracleSQLParserFlexLexer(getTokenTypes()));
	}
}
