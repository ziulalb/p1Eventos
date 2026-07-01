@Service
public class TokenService {
    private String secret = "sua-chave-secreta-super-segura-com-muitos-caracteres";

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getMatricula())
                .claim("roles", usuario.getPerfis().stream().map(Perfil::getNome).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String validarToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
