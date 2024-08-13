import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DemissaoFuncionarioDetailComponent } from './demissao-funcionario-detail.component';

describe('DemissaoFuncionario Management Detail Component', () => {
  let comp: DemissaoFuncionarioDetailComponent;
  let fixture: ComponentFixture<DemissaoFuncionarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemissaoFuncionarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DemissaoFuncionarioDetailComponent,
              resolve: { demissaoFuncionario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DemissaoFuncionarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemissaoFuncionarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demissaoFuncionario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DemissaoFuncionarioDetailComponent);

      // THEN
      expect(instance.demissaoFuncionario()).toEqual(expect.objectContaining({ id: 123 }));
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
