import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GatewayAssinaturaEmpresaDetailComponent } from './gateway-assinatura-empresa-detail.component';

describe('GatewayAssinaturaEmpresa Management Detail Component', () => {
  let comp: GatewayAssinaturaEmpresaDetailComponent;
  let fixture: ComponentFixture<GatewayAssinaturaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GatewayAssinaturaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GatewayAssinaturaEmpresaDetailComponent,
              resolve: { gatewayAssinaturaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GatewayAssinaturaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GatewayAssinaturaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gatewayAssinaturaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GatewayAssinaturaEmpresaDetailComponent);

      // THEN
      expect(instance.gatewayAssinaturaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
