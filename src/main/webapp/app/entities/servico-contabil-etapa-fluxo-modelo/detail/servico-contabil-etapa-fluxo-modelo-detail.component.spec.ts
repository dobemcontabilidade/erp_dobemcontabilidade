import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServicoContabilEtapaFluxoModeloDetailComponent } from './servico-contabil-etapa-fluxo-modelo-detail.component';

describe('ServicoContabilEtapaFluxoModelo Management Detail Component', () => {
  let comp: ServicoContabilEtapaFluxoModeloDetailComponent;
  let fixture: ComponentFixture<ServicoContabilEtapaFluxoModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicoContabilEtapaFluxoModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ServicoContabilEtapaFluxoModeloDetailComponent,
              resolve: { servicoContabilEtapaFluxoModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ServicoContabilEtapaFluxoModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicoContabilEtapaFluxoModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load servicoContabilEtapaFluxoModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ServicoContabilEtapaFluxoModeloDetailComponent);

      // THEN
      expect(instance.servicoContabilEtapaFluxoModelo()).toEqual(expect.objectContaining({ id: 123 }));
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
