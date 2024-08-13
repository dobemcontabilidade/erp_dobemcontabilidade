import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServicoContabilEmpresaModeloDetailComponent } from './servico-contabil-empresa-modelo-detail.component';

describe('ServicoContabilEmpresaModelo Management Detail Component', () => {
  let comp: ServicoContabilEmpresaModeloDetailComponent;
  let fixture: ComponentFixture<ServicoContabilEmpresaModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicoContabilEmpresaModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ServicoContabilEmpresaModeloDetailComponent,
              resolve: { servicoContabilEmpresaModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ServicoContabilEmpresaModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicoContabilEmpresaModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load servicoContabilEmpresaModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ServicoContabilEmpresaModeloDetailComponent);

      // THEN
      expect(instance.servicoContabilEmpresaModelo()).toEqual(expect.objectContaining({ id: 123 }));
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
