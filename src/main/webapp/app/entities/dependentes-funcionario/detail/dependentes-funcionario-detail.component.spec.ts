import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DependentesFuncionarioDetailComponent } from './dependentes-funcionario-detail.component';

describe('DependentesFuncionario Management Detail Component', () => {
  let comp: DependentesFuncionarioDetailComponent;
  let fixture: ComponentFixture<DependentesFuncionarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DependentesFuncionarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DependentesFuncionarioDetailComponent,
              resolve: { dependentesFuncionario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DependentesFuncionarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DependentesFuncionarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dependentesFuncionario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DependentesFuncionarioDetailComponent);

      // THEN
      expect(instance.dependentesFuncionario()).toEqual(expect.objectContaining({ id: 123 }));
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
