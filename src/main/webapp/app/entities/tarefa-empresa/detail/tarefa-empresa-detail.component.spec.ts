import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaEmpresaDetailComponent } from './tarefa-empresa-detail.component';

describe('TarefaEmpresa Management Detail Component', () => {
  let comp: TarefaEmpresaDetailComponent;
  let fixture: ComponentFixture<TarefaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaEmpresaDetailComponent,
              resolve: { tarefaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaEmpresaDetailComponent);

      // THEN
      expect(instance.tarefaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
