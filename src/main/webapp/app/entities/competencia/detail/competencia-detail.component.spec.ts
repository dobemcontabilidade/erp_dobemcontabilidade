import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompetenciaDetailComponent } from './competencia-detail.component';

describe('Competencia Management Detail Component', () => {
  let comp: CompetenciaDetailComponent;
  let fixture: ComponentFixture<CompetenciaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompetenciaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CompetenciaDetailComponent,
              resolve: { competencia: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CompetenciaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetenciaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load competencia on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CompetenciaDetailComponent);

      // THEN
      expect(instance.competencia()).toEqual(expect.objectContaining({ id: 123 }));
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
