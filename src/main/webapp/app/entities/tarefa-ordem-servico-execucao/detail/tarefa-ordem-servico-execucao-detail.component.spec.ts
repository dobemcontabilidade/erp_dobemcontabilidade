import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaOrdemServicoExecucaoDetailComponent } from './tarefa-ordem-servico-execucao-detail.component';

describe('TarefaOrdemServicoExecucao Management Detail Component', () => {
  let comp: TarefaOrdemServicoExecucaoDetailComponent;
  let fixture: ComponentFixture<TarefaOrdemServicoExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaOrdemServicoExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaOrdemServicoExecucaoDetailComponent,
              resolve: { tarefaOrdemServicoExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaOrdemServicoExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaOrdemServicoExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaOrdemServicoExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaOrdemServicoExecucaoDetailComponent);

      // THEN
      expect(instance.tarefaOrdemServicoExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
