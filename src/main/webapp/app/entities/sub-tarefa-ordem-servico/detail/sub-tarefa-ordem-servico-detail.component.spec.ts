import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SubTarefaOrdemServicoDetailComponent } from './sub-tarefa-ordem-servico-detail.component';

describe('SubTarefaOrdemServico Management Detail Component', () => {
  let comp: SubTarefaOrdemServicoDetailComponent;
  let fixture: ComponentFixture<SubTarefaOrdemServicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubTarefaOrdemServicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SubTarefaOrdemServicoDetailComponent,
              resolve: { subTarefaOrdemServico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SubTarefaOrdemServicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubTarefaOrdemServicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subTarefaOrdemServico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SubTarefaOrdemServicoDetailComponent);

      // THEN
      expect(instance.subTarefaOrdemServico()).toEqual(expect.objectContaining({ id: 123 }));
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
