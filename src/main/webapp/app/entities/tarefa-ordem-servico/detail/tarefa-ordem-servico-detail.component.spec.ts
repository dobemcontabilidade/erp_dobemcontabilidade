import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaOrdemServicoDetailComponent } from './tarefa-ordem-servico-detail.component';

describe('TarefaOrdemServico Management Detail Component', () => {
  let comp: TarefaOrdemServicoDetailComponent;
  let fixture: ComponentFixture<TarefaOrdemServicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaOrdemServicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaOrdemServicoDetailComponent,
              resolve: { tarefaOrdemServico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaOrdemServicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaOrdemServicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaOrdemServico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaOrdemServicoDetailComponent);

      // THEN
      expect(instance.tarefaOrdemServico()).toEqual(expect.objectContaining({ id: 123 }));
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
