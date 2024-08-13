import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServicoContabilAssinaturaEmpresaDetailComponent } from './servico-contabil-assinatura-empresa-detail.component';

describe('ServicoContabilAssinaturaEmpresa Management Detail Component', () => {
  let comp: ServicoContabilAssinaturaEmpresaDetailComponent;
  let fixture: ComponentFixture<ServicoContabilAssinaturaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicoContabilAssinaturaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ServicoContabilAssinaturaEmpresaDetailComponent,
              resolve: { servicoContabilAssinaturaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ServicoContabilAssinaturaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicoContabilAssinaturaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load servicoContabilAssinaturaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ServicoContabilAssinaturaEmpresaDetailComponent);

      // THEN
      expect(instance.servicoContabilAssinaturaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
