import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServicoContabilEtapaFluxoExecucaoDetailComponent } from './servico-contabil-etapa-fluxo-execucao-detail.component';

describe('ServicoContabilEtapaFluxoExecucao Management Detail Component', () => {
  let comp: ServicoContabilEtapaFluxoExecucaoDetailComponent;
  let fixture: ComponentFixture<ServicoContabilEtapaFluxoExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicoContabilEtapaFluxoExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ServicoContabilEtapaFluxoExecucaoDetailComponent,
              resolve: { servicoContabilEtapaFluxoExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ServicoContabilEtapaFluxoExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicoContabilEtapaFluxoExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load servicoContabilEtapaFluxoExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ServicoContabilEtapaFluxoExecucaoDetailComponent);

      // THEN
      expect(instance.servicoContabilEtapaFluxoExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
